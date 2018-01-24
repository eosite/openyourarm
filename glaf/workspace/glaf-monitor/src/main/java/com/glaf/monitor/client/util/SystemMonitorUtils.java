package com.glaf.monitor.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.monitor.client.domain.FileSystemExt;
import com.glaf.monitor.client.domain.SystemResource;

public class SystemMonitorUtils {
	protected static final Log logger = LogFactory.getLog(SystemMonitorUtils.class);

	protected static int CPUTIME = 500;
	protected static int PERCENT = 100;
	private static final int FAULTLENGTH = 10;
	
	private static Sigar sigar = new Sigar();
	
	public static JSONObject getCpu(){
		return null;
	}
	public static JSONObject getComputerBaseData(){
		JSONObject ret = new JSONObject();
		// 获取计算机信息
		Map<String, String> map = System.getenv();
		ret.put("userName", map.get("USERNAME")); // 当前计算机的用户名
		ret.put("computerName", map.get("COMPUTERNAME"));// 获取计算机名
		// 操作系统信息
		OperatingSystem os = OperatingSystem.getInstance();
		// 操作系统内核类型如：386、486、586等x86
		ret.put("osarch", os.getArch());
		ret.put("oscpuEndian", os.getCpuEndian());
		ret.put("osdataModel", os.getDataModel());
		// 系统描述
		ret.put("description", os.getDescription());
		ret.put("machine", os.getMachine());
		// 操作系统类型
		ret.put("name", os.getName());
		ret.put("patchLevel", os.getPatchLevel());
		// 操作系统的卖主
		ret.put("vendor", os.getVendor());
		// 操作系统名称
		ret.put("vendorName", os.getVendorName());
		// 操作系统卖主类型
		ret.put("vendorVersion", os.getVendorVersion());
		// 操作系统的版本号
		ret.put("version", os.getVersion());
		
		return ret;
	}
	//获取服务器的预警项
	public static JSONObject getComputerItemDataByCode(String code,JSONObject params){
		JSONObject ret = null;
		try{
			switch(code){
				case "cpu" : ret = getCpuInfo();break;
				case "mem" : ret = getMemUsage();break;
				case "disk" : ret = getFileSystemUsage(params.getString("dir"));break;
				case "temp" : break;
				case "fans" : break;
				case "os" : ret = getComputerBaseData();break;
				default : return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取信息("+code+")异常");
		}
		return ret;
	}
	public static JSONObject getCpuInfo() throws Exception{
		JSONObject data = new JSONObject();
		
		CpuInfo[] infos = sigar.getCpuInfoList();//  获取cpu信息
        CpuInfo info = infos[0]; 
        data.put("vendor", info.getVendor());// 芯片供应商
        data.put("model", info.getModel());// CPU型号 
        data.put("mhz", info.getMhz());// 主频  
        data.put("totalCores", info.getTotalCores());// CPU逻辑个数  
        if ((info.getTotalCores() != info.getTotalSockets()) || (info.getCoresPerSocket() > info.getTotalCores())) {  
        	data.put("totalSockets", info.getTotalSockets());// CPU物理个数
        	data.put("coresPerSocket", info.getTotalSockets());// 每个CPU核数  
        } 
        long cacheSize = info.getCacheSize();  
        if (cacheSize != Sigar.FIELD_NOTIMPL) {  
        	data.put("cacheSize", cacheSize);// 缓存  
        }  
        
    	// 整个CPU使用情况  
        CpuPerc cpu = sigar.getCpuPerc();
        data.put("combined", cpu.getCombined());// CPU总的使用率
		data.put("idle", cpu.getIdle());// 当前空闲率(CPU空闲时间)
		data.put("wait", cpu.getWait());// 当前等待率(// CPU等待时间)
		data.put("sys", cpu.getSys());// 系统使用率(系统态使用的CPU百分比)
		data.put("user", cpu.getUser());// 用户使用率(用户态使用的CPU百分比)
		
		return data;
	}
	
	/**
	 * 功能：物理内存信息
	 * @throws Exception 
	 */
	public static JSONObject getMemUsage() throws Exception {
		JSONObject ret = new JSONObject();
		Mem mem = sigar.getMem(); // 内存
		ret.put("total", mem.getTotal()); // 内存总量
		ret.put("free", mem.getFree()); // 可用
		ret.put("freePercent", mem.getFreePercent()); // 可用
		ret.put("used", mem.getUsed()); // 已用
		ret.put("usedPercent", mem.getUsedPercent()); // 已用百分比
		ret.put("unit", "b"); // 单位
		return ret;
	}
	
	public static JSONObject getTotalSystemUsage(){
		JSONObject ret = new JSONObject(),item = null;
		JSONArray data = new JSONArray();
		FileSystem fslist[] = null;
		try {
			// 全部磁盘的总信息
			Long total = 0L, free = 0L, used = 0L;
			// 获取各个硬盘信息
			fslist = sigar.getFileSystemList();
			for (int i = 0; i < fslist.length; i++) {
				System.out.println("分区的盘符名称" + i);
				FileSystemUsage usage = null;
				try {
					// 根据目录获取对应的磁盘信息
					usage = sigar.getFileSystemUsage(fslist[i].getDirName());
				} catch (SigarException e) {
					if (fslist[i].getType() == 2)
						throw e;
					continue;
				}
				switch (fslist[i].getType()) {
				case 0: // TYPE_UNKNOWN ：未知
					break;
				case 1: // TYPE_NONE
					break;
				case 2: // TYPE_LOCAL_DISK : 本地硬盘
					item = new JSONObject();
					item.put("num", i);
					total += usage.getTotal();
					free += usage.getFree();
					used += usage.getUsed();
					item.put("total", usage.getTotal()); // 内存总量,单位kb
					item.put("free", usage.getFree()); // 可用
					item.put("used", usage.getUsed()); // 已用
					item.put("usedPercent", usage.getUsePercent()); // 已用百分比
					item.put("type", fslist[i].getSysTypeName());//磁盘的类型，如 FAT32、NTFS
					data.add(item);
					break;
				case 3:// TYPE_NETWORK ：网络
					break;
				case 4:// TYPE_RAM_DISK ：闪存
					break;
				case 5:// TYPE_CDROM ：光驱
					break;
				case 6:// TYPE_SWAP ：页面交换
					break;
				}
			}
			ret.put("data", data);
			ret.put("total", total*1024);
			ret.put("free", free*1024);
			ret.put("used", used*1024);
			ret.put("freePercent", free*1.0/total*100); // 可用
			ret.put("usedPercent", used*1.0/total*100); // 已用百分比
			ret.put("unit", "b");
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
	/**
	 * 获取文件系统的信息，如硬盘的使用情况
	 * 
	 * @param sigar
	 */
	public static JSONObject getFileSystemUsage(String dirName) {
		if(StringUtils.isNotEmpty(dirName)){
			try {  
				FileSystemUsage usage = sigar.getFileSystemUsage(dirName);  
				JSONObject ret = new JSONObject();
				ret.put("total", usage.getTotal()); // 内存总量,单位kb
				ret.put("free", usage.getFree()); // 可用
				ret.put("used", usage.getUsed()); // 已用
				ret.put("usedPercent", usage.getUsePercent()); // 已用百分比
				return ret;
			} catch (SigarException e) { 
				e.printStackTrace();
			} 
			return null;
		}else{
			return getTotalSystemUsage();
		}
	}
	
	/**
	 * 获取文件系统的信息，如硬盘的使用情况
	 * 
	 * @param sigar
	 */
	public static JSONObject getFileSystemInfo(String dir) {
		JSONObject ret = new JSONObject();
		JSONArray data = new JSONArray();
		JSONObject item = null;
		FileSystem fslist[] = null;
		try {
			// 全部磁盘的总信息
			Long total = 0L, free = 0L, used = 0L;

			// 获取各个硬盘信息
			fslist = sigar.getFileSystemList();
			FileSystemExt fsExt = null;
			for (int i = 0; i < fslist.length; i++) {
				System.out.println("分区的盘符名称" + i);
				FileSystemUsage usage = null;
				try {
					// 根据目录获取对应的磁盘信息
					usage = sigar.getFileSystemUsage(fslist[i].getDirName());
				} catch (SigarException e) {
					if (fslist[i].getType() == 2)
						throw e;
					continue;
				}
				switch (fslist[i].getType()) {
				case 0: // TYPE_UNKNOWN ：未知
					break;
				case 1: // TYPE_NONE
					break;
				case 2: // TYPE_LOCAL_DISK : 本地硬盘
					item = new JSONObject();
					item.put("num", i);
					total += usage.getTotal();
					free += usage.getFree();
					used += usage.getUsed();
					item.put("total", usage.getTotal()); // 内存总量,单位kb
					item.put("free", usage.getFree()); // 可用
					item.put("used", usage.getUsed()); // 已用
					item.put("usedPercent", usage.getUsePercent()); // 已用百分比
					item.put("type", fslist[i].getSysTypeName());//磁盘的类型，如 FAT32、NTFS
					data.add(item);
					break;
				case 3:// TYPE_NETWORK ：网络
					break;
				case 4:// TYPE_RAM_DISK ：闪存
					break;
				case 5:// TYPE_CDROM ：光驱
					break;
				case 6:// TYPE_SWAP ：页面交换
					break;
				}
			}
			ret.put("data", data);
			ret.put("total", total);
			ret.put("free", free);
			ret.put("used", used);
			ret.put("unit", "Kb");
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
	
	/**
	 * 功能：获取Linux和Window系统cpu使用率
	 * 
	 * window原理 windows系统中有获取cpu使用率的可执行文件exe，只要在java中获取该文件的执行路径，通过Java调用即可。
	 * 调用java的Runtime.getRuntime().exec执行cmd应用程序
	 * 利用java中sleep来计算睡眠前后cpu的忙碌时间与空闲时间，因为sleep不会释放系统资源 根据忙碌时间占总时间的比例来计算cpu使用率
	 * 
	 */
	public static JSONObject getCpuUsage(String osName) {
		JSONObject ret = new JSONObject();
		// 如果是window系统
		if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
			try {
				String procCmd = System.getenv("windir")
						+ "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
				// 取进程信息
				long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));// 第一次读取CPU信息
				Thread.sleep(CPUTIME);// 睡500ms
				long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));// 第二次读取CPU信息
				if (c0 != null && c1 != null) {
					long idletime = c1[0] - c0[0];// 空闲时间
					ret.put("free", (c1[0] + c0[0]) / 2);
					long busytime = c1[1] - c0[1];// 使用时间
					ret.put("use", (c1[1] + c0[1]) / 2);
					Double cpusage = Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime));

					ret.put("used", cpusage);
					ret.put("total", 100);
					ret.put("free", 100 - cpusage);

					// BigDecimal b1 = new BigDecimal(cpusage);
					// double cpuUsage = b1.setScale(2,
					// BigDecimal.ROUND_HALF_UP).doubleValue();

					return ret;
				} else {
					return null;
				}
			} catch (Exception ex) {
				logger.debug(ex);
				return null;
			}

		} else {

			try {
				Map<?, ?> map1 = cpuinfo();
				Thread.sleep(CPUTIME);
				Map<?, ?> map2 = cpuinfo();

				long user1 = Long.parseLong(map1.get("user").toString());
				long nice1 = Long.parseLong(map1.get("nice").toString());
				long system1 = Long.parseLong(map1.get("system").toString());
				long idle1 = Long.parseLong(map1.get("idle").toString());

				long user2 = Long.parseLong(map2.get("user").toString());
				long nice2 = Long.parseLong(map2.get("nice").toString());
				long system2 = Long.parseLong(map2.get("system").toString());
				long idle2 = Long.parseLong(map2.get("idle").toString());

				long total1 = user1 + system1 + nice1;
				long total2 = user2 + system2 + nice2;
				float total = total2 - total1;

				long totalIdle1 = user1 + nice1 + system1 + idle1;
				long totalIdle2 = user2 + nice2 + system2 + idle2;
				float totalidle = totalIdle2 - totalIdle1;

				float cpusage = (total / totalidle) * 100;

				// BigDecimal b1 = new BigDecimal(cpusage);
				// double cpuUsage = b1.setScale(2,
				// BigDecimal.ROUND_HALF_UP).doubleValue();

				ret.put("used", cpusage);
				ret.put("total", 100);
				ret.put("free", 100 - cpusage);

				return ret;
			} catch (InterruptedException e) {
				logger.debug(e);
			}
		}
		return null;
	}

	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 * 
	 * @param src
	 *            要截取的字符串
	 * @param start_idx
	 *            开始坐标（包括该坐标)
	 * @param end_idx
	 *            截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	/**
	 * 从字符串文本中获得数字
	 * 
	 * @param text
	 * @return
	 */
	private static List<String> getDigit(String text) {
		List<String> digitList = new ArrayList<String>();
		digitList.add(text.replaceAll("\\D", ""));
		return digitList;
	}

	// window读取cpu相关信息
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;// 读取物理设备时间
			long usertime = 0;// 执行代码占用时间
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount
				String caption = substring(line, capidx, cmdidx - 1).trim();
				// System.out.println("caption:"+caption);
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				// System.out.println("cmd:"+cmd);
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				List<String> digitS1 = getDigit(s1);
				List<String> digitS2 = getDigit(s2);

				// System.out.println("s1:"+digitS1.get(0));
				// System.out.println("s2:"+digitS2.get(0));

				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0) {
						if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
							idletime += Long.valueOf(digitS1.get(0)).longValue();
						}
					}
					if (s2.length() > 0) {
						if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
							idletime += Long.valueOf(digitS2.get(0)).longValue();
						}
					}
					continue;
				}
				if (s1.length() > 0) {
					if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
						kneltime += Long.valueOf(digitS1.get(0)).longValue();
					}
				}

				if (s2.length() > 0) {
					if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
						kneltime += Long.valueOf(digitS2.get(0)).longValue();
					}
				}
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;

			return retn;
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				logger.debug(e);
			}
		}
		return null;
	}

	/**
	 * Linux的CPU信息，放在/proc/stat文件下 功能：Linux CPU使用信息
	 */
	public static Map<?, ?> cpuinfo() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith("cpu")) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					List<String> temp = new ArrayList<String>();
					while (tokenizer.hasMoreElements()) {
						String value = tokenizer.nextToken();
						temp.add(value);
					}
					map.put("user", temp.get(1));
					map.put("nice", temp.get(2));
					map.put("system", temp.get(3));
					map.put("idle", temp.get(4));
					map.put("iowait", temp.get(5));
					map.put("irq", temp.get(6));
					map.put("softirq", temp.get(7));
					map.put("stealstolen", temp.get(8));
					break;
				}
			}
		} catch (Exception e) {
			logger.debug(e);
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				logger.debug(e2);
			}
		}
		return map;
	}

	public static JSONObject getMonitorSystemInfo(){
		JSONObject allData = new JSONObject();

		BufferedWriter bufferedWriter = LogUtils.getProcLogWriter();
		try {
			LogUtils.writeLog(bufferedWriter, "开始获取操作系统基础信息");

			Properties props = System.getProperties();
			// 获取JAVA信息
			JSONObject item = new JSONObject();
			item.put("jvmName", props.getProperty("java.vm.name"));
			item.put("jvmVersion", props.getProperty("java.vm.specification.version"));
			item.put("javaHome", props.getProperty("java.home"));
			item.put("javaVersion", props.getProperty("java.version"));
			allData.put("java", item);

			// 获取计算机信息
			Map<String, String> map = System.getenv();
			allData.put("userName", map.get("USERNAME")); // 当前计算机的用户名
			allData.put("computerName", map.get("COMPUTERNAME"));// 获取计算机名

			// 操作系统
			item = getOSInfo();
			allData.put("os", item);
			String osName = (String) item.get("name");

			Sigar sigar = new Sigar();
			// CPU
			item = getCpuInfo(sigar);

			allData.put("cpu", item);

			// 内存 memory
			item = getMemUsage(osName,sigar);
			allData.put("mem", item);

			// 交换区 swap
			allData.put("swap", getSwapInfo(sigar));
			// 硬盘
//			item = getFileSystemInfo();
			allData.put("disk", item);
			
			// 其他
			allData.put("other", getEthernetInfo(sigar));

			LogUtils.writeLog(bufferedWriter, "成功获取操作系统基础信息!");
			LogUtils.writeLog(bufferedWriter, "操作系统信息如下:");
			LogUtils.writeLog(bufferedWriter, allData.toJSONString());
		} catch (Exception e) {
			LogUtils.closeBufferedWriter(bufferedWriter);
		}
		return allData;
	}
	/**
	 * 获取运行环境信息 terminal.setOsName(item.getString("osName")); //操作系统名称
	 * 
	 * 
	 * @return
	 */
	public static JSONObject getSystemInfo() {
		JSONObject ret = new JSONObject();
		JSONObject allData = new JSONObject();

		BufferedWriter bufferedWriter = LogUtils.getProcLogWriter();
		try {
			LogUtils.writeLog(bufferedWriter, "开始获取操作系统基础信息");

			Properties props = System.getProperties();
			// 获取JAVA信息
			JSONObject item = new JSONObject();
			item.put("jvmName", props.getProperty("java.vm.name"));
			item.put("jvmVersion", props.getProperty("java.vm.specification.version"));
			item.put("javaHome", props.getProperty("java.home"));
			item.put("javaVersion", props.getProperty("java.version"));
			allData.put("java", item);

			// 获取计算机信息
			Map<String, String> map = System.getenv();
			allData.put("userName", map.get("USERNAME")); // 当前计算机的用户名
			allData.put("computerName", map.get("COMPUTERNAME"));// 获取计算机名

			// 操作系统
			item = getOSInfo();
			allData.put("os", item);
			String osName = (String) item.get("name");
			ret.put("osName", item.get("name"));// 操作系统厂家
			ret.put("osFac", item.get("vendor"));// 操作系统厂家
			ret.put("osVer", item.get("version"));// 操作系统版本

			Sigar sigar = new Sigar();
			// CPU
			item = getCpuInfo(sigar);

			allData.put("cpu", item);
			ret.put("cpuFac", item.get("cpuVendor"));// CPU厂家
			ret.put("cpuCores", item.get("cpuProcessors"));// CPU核数
			ret.put("coreMhz", item.get("cpuMhz"));// 单核频率

			// 内存 memory
			item = getMemUsage(osName,sigar);
			allData.put("mem", item);
			ret.put("memType", "");// 内存类型
			ret.put("memSize", item.get("total"));// 内存大小

			// 交换区 swap
			allData.put("swap", getSwapInfo(sigar));
			// 硬盘
//			item = getFileSystemInfo(sigar);
//			allData.put("disk", item);
//			ret.put("diskType", item.get("type"));// 磁盘类型
//			ret.put("diskSize", item.get("total"));// 内存大小
			
			// 其他
			allData.put("other", getEthernetInfo(sigar));

			LogUtils.writeLog(bufferedWriter, "成功获取操作系统基础信息!");
			LogUtils.writeLog(bufferedWriter, "操作系统信息如下:");
			ret.put("all", allData);
			LogUtils.writeLog(bufferedWriter, allData.toJSONString());
		} catch (Exception e) {
			LogUtils.closeBufferedWriter(bufferedWriter);
		}
		return ret;
	}

	// 一些其他的信息
	public static JSONObject getEthernetInfo(Sigar sigar) {
		JSONObject item = new JSONObject();
		try {
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				item.put("address", cfg.getAddress());// IP地址
				item.put("broadcast", cfg.getBroadcast());// 网关广播地址
				item.put("hwaddr", cfg.getHwaddr());// 网卡MAC地址
				item.put("netmask", cfg.getNetmask());// 子网掩码
				item.put("description", cfg.getDescription());// 网卡描述信息
				item.put("flags", cfg.getFlags());
				item.put("metric", cfg.getMetric());
				item.put("mtu", cfg.getMtu());
				item.put("name", cfg.getName());
				item.put("address", cfg.getAddress());
				item.put("address", cfg.getAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error whilecreating GUID" + e);
		} finally {
			if (sigar != null)
				sigar.close();
		}
		return item;
	}

	/**
	 * 设置CPU信息
	 * 
	 * @param systemResource
	 * @param sigar
	 */
	public static JSONObject getCpuInfo(Sigar sigar) {
		JSONObject item = new JSONObject();
		CpuInfo infos[] = null;
		CpuPerc cpuList[] = null;
		try {
			infos = sigar.getCpuInfoList();
			cpuList = sigar.getCpuPercList();
			if (infos != null && infos.length > 0) {
				item.put("cpuMhz", infos[0].getMhz());// CPU的总量MHz
				item.put("cpuModel", infos[0].getModel());// 获得CPU的类别，如：Celeron
				item.put("cpuProcessors", infos.length);//核数
				item.put("cpuCacheSize", infos[0].getCacheSize());//缓冲存储器数量
				item.put("cpuVendor", infos[0].getVendor());// 获得CPU的卖主，如：Intel
				
				item.put("cpuCombined", cpuList[0].getCombined());// CPU总的使用率
				item.put("cpuIdle", cpuList[0].getIdle());// 当前空闲率
				item.put("cpuWait", cpuList[0].getWait());// 当前等待率
				item.put("cpuSys", cpuList[0].getSys());// 系统使用率
				item.put("cpuUser", cpuList[0].getUser());// 用户使用率
			}
//			// 获取CPU的使用率
//			JSONObject ret = getCpuUsage(getOsName());
//			if (ret != null) {
//				item.put("used", ret.get("used"));
//				item.put("total", ret.get("total"));
//				item.put("free", ret.get("free"));
//			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	public static String getOsName() {
		// 操作系统信息
		OperatingSystem os = OperatingSystem.getInstance();
		return os.getName();
	}

	/**
	 * 设置操作系统信息
	 */
	public static JSONObject getOSInfo() {
		JSONObject item = new JSONObject();
		// 操作系统信息
		OperatingSystem os = OperatingSystem.getInstance();
		// 获取JAVA信息
		item = new JSONObject();
		// 操作系统内核类型如：386、486、586等x86
		item.put("arch", os.getArch());
		item.put("cpuEndian", os.getCpuEndian());
		item.put("dataModel", os.getDataModel());
		// 系统描述
		item.put("description", os.getDescription());
		item.put("machine", os.getMachine());
		// 操作系统类型
		item.put("name", os.getName());
		item.put("patchLevel", os.getPatchLevel());
		// 操作系统的卖主
		item.put("vendor", os.getVendor());
		// 操作系统名称
		item.put("vendorName", os.getVendorName());
		// 操作系统卖主类型
		item.put("vendorVersion", os.getVendorVersion());
		// 操作系统的版本号
		item.put("version", os.getVersion());

		return item;
	}

	/**
	 * 功能：Linux 和 Window 内存使用率
	 */
	public static JSONObject getMemUsage(String osName,Sigar sigar) {
		JSONObject ret = new JSONObject();
		if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {

			try {
				Mem mem = sigar.getMem(); // 内存
				ret.put("total", mem.getTotal()); // 内存总量
				ret.put("free", mem.getFree()); // 可用
				ret.put("freePercent", mem.getFreePercent()); // 可用
				ret.put("used", mem.getUsed()); // 已用
				ret.put("usedPercent", mem.getUsedPercent()); // 已用百分比
				ret.put("unit", "b"); // 单位
				return ret;
			} catch (Exception e) {
				logger.debug(e);
			}
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			InputStreamReader inputs = null;
			BufferedReader buffer = null;
			try {
				Mem mem = sigar.getMem(); // 内存
				ret.put("total", mem.getTotal()); // 内存总量
				ret.put("free", mem.getFree()); // 可用
				ret.put("freePercent", mem.getFreePercent()); // 可用
				ret.put("used", mem.getUsed()); // 已用
				ret.put("usedPercent", mem.getUsedPercent()); // 已用百分比
				ret.put("unit", "b"); // 单位
				return ret;
				
//				inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
//				buffer = new BufferedReader(inputs);
//				String line = "";
//				while (true) {
//					line = buffer.readLine();
//					if (line == null)
//						break;
//					int beginIndex = 0;
//					int endIndex = line.indexOf(":");
//					if (endIndex != -1) {
//						String key = line.substring(beginIndex, endIndex);
//						beginIndex = endIndex + 1;
//						endIndex = line.length();
//						String memory = line.substring(beginIndex, endIndex);
//						String value = memory.replace("kB", "").trim();
//						map.put(key, value);
//					}
//				}
//
//				long memTotal = Long.parseLong(map.get("MemTotal").toString());
//				long memFree = Long.parseLong(map.get("MemFree").toString());
//				long memused = memTotal - memFree;
//				long buffers = Long.parseLong(map.get("Buffers").toString());
//				long cached = Long.parseLong(map.get("Cached").toString());
//				
//				ret.put("total", memTotal); // 内存总量
//				ret.put("free", memFree); // 可用
//				ret.put("buffers", buffers); // 缓存
//				ret.put("used", memused); // 已用
//				ret.put("cached", cached); // 高速缓存
//				ret.put("unit", "Kb"); // 单位
//				return ret;

			} catch (Exception e) {
				logger.debug(e);
			} finally {
				try {
					buffer.close();
					inputs.close();
				} catch (Exception e2) {
					logger.debug(e2);
				}
			}

		}
		return ret;
	}

	/**
	 * 设置内存信息
	 * 
	 * @param systemResource
	 * @param sigar
	 */
	public static JSONObject getMemoryInfo(Sigar sigar) {
		JSONObject item = new JSONObject();
		try {
			Mem mem = sigar.getMem(); // 内存
			item.put("total", mem.getTotal()); // 内存总量
			item.put("free", mem.getFree()); // 可用
			item.put("freePercent", mem.getFreePercent()); // 可用
			item.put("used", mem.getUsed()); // 已用
			item.put("usedPercent", mem.getUsedPercent()); // 已用百分比
			item.put("unit", "b"); // 单位
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 设置交换区信息
	 * 
	 * @param systemResource
	 * @param sigar
	 */
	public static JSONObject getSwapInfo(Sigar sigar) {
		JSONObject item = new JSONObject();
		try {
			Swap swap = sigar.getSwap(); // 交换区
			item.put("total", swap.getTotal()); // 内存总量
			item.put("free", swap.getFree()); // 可用
			item.put("used", swap.getUsed()); // 已用
			item.put("unit", "b"); // 单位
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * Window 和Linux 得到磁盘的使用率
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getDiskUsage(String osName) throws Exception {
		JSONObject ret = new JSONObject();
		if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
			long allTotal = 0;
			long allFree = 0;
			long allUsed = 0;
			for (char c = 'A'; c <= 'Z'; c++) {
				String dirName = c + ":/";
				File win = new File(dirName);
				if (win.exists()) {
					long total = (long) win.getTotalSpace();
					long free = (long) win.getFreeSpace();
					long used = win.getUsableSpace();
					allTotal = allTotal + total;
					allFree = allFree + free;
					allUsed += used;
				}
			}
			ret.put("used", allUsed);
			ret.put("free", allFree);
			ret.put("total", allTotal);
			ret.put("totalGB", allUsed / 1024);
			ret.put("unit", "Mb");
			return ret;
		} else {
			double totalHD = 0;
			double usedHD = 0;
			double freeHD = 0;
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("df -hl /home");// df -hl 查看硬盘空间
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str = null;
				String[] strArray = null;
				logger.info("开始获取硬盘大小---------------------------------------");
				while ((str = in.readLine()) != null) {
					int m = 0;
					logger.info(str);
					strArray = str.split(" ");
					for (String tmp : strArray) {
						if (tmp.trim().length() == 0)
							continue;
						++m;
						logger.info("tmp");
						if (tmp.indexOf("G") != -1) {
							if (m == 2) {
								if (!tmp.equals("") && !tmp.equals("0"))
									totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
							}
							if (m == 3) {
								if (!tmp.equals("none") && !tmp.equals("0"))
									usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
							}
							if (m == 4) {
								if (!tmp.equals("none") && !tmp.equals("0"))
									freeHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
							}
						}
						if (tmp.indexOf("M") != -1) {
							if (m == 2) {
								if (!tmp.equals("") && !tmp.equals("0"))
									totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
							}
							if (m == 3) {
								if (!tmp.equals("none") && !tmp.equals("0"))
									usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
							}
							if (m == 4) {
								if (!tmp.equals("none") && !tmp.equals("0"))
									freeHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
							}
						}
						logger.info("totalHD:"+totalHD+",usedHD:"+usedHD+",freeHD"+freeHD);
					}
				}
			} catch (Exception e) {
				logger.debug(e);
			} finally {
				in.close();
			}
			ret.put("totalGB", usedHD / 1024);
			ret.put("used", usedHD);
			ret.put("free", freeHD);
			ret.put("total", totalHD);
			ret.put("unit", "Mb");

			return ret;
		}

	}

	/**
	 * 获取目录空间利用情况
	 * 
	 * @param dir
	 * @return
	 */
	public static DirUsage getDirUsage(String dir) {
		Sigar sigar = new Sigar();
		try {
			return sigar.getDirUsage(dir);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void getApplication(SystemResource systemResource, Sigar sigar) {
		try {
			// tomcat
			long tomcatPid = sigar.getServicePid("tomcat");
			// sigar.kill(tomcatPid, 1); //关闭进程
			// resin
			long resinPid = sigar.getServicePid("resin");
			// jetty
			long jettyPid = sigar.getServicePid("jetty");
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取进程运行路径
	 * 
	 * @param pName
	 *            进程名称
	 * @return
	 */
	public static String getProcessDirectory(String pName) {
		String directory = null;
		try {
			Sigar sigar = new Sigar();
			long pid = sigar.getServicePid(pName);
			directory = sigar.getProcExe(pid).getName();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return directory;
	}

	/**
	 * 获取端口进程
	 * 
	 * @param pName
	 *            进程名称
	 * @return
	 */
	public static long getProcessByPort(int protocol, long port) {
		long pid = 0l;
		try {
			Sigar sigar = new Sigar();
			pid = sigar.getProcPort(protocol, port);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pid;
	}

	/**
	 * 关闭进程
	 * 
	 * @param pName
	 *            进程名称
	 */
	public static void killProcess(String pName) {

		long pid = 0l;
		try {
			Sigar sigar = new Sigar();
			pid = sigar.getServicePid(pName);
			sigar.kill(pid, 1);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 关闭进程
	 * 
	 * @param pid
	 *            进程id
	 */
	public static void killProcess(long pid) {
		Sigar sigar = new Sigar();
		try {
			sigar.kill(pid, 1);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取进程环境变量
	 * 
	 * @param pName
	 * @return
	 */
	public static Map getProcessEnv(String pName) {
		Map envMap = null;
		long pid = 0l;
		try {
			Sigar sigar = new Sigar();
			pid = sigar.getServicePid(pName);
			envMap = sigar.getProcEnv(pid);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return envMap;
	}

	/**
	 * 获取路径所在分区空间使用情况
	 * 
	 * @param dir
	 * @return
	 */
	public static FileSystemUsage getDirFileSystemUsage(String dir) {
		Sigar sigar = new Sigar();
		File dirDoc = new File(dir);
		try {
			if (!dirDoc.exists()) {
				dirDoc.mkdirs();
			}
			FileSystemUsage usage = sigar.getFileSystemUsage(dir);
			return usage;
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());

		}
		return null;
	}

}
