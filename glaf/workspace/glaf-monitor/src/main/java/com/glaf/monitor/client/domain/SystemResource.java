package com.glaf.monitor.client.domain;

import java.io.Serializable;
import java.util.List;

import org.hyperic.sigar.OperatingSystem;

import com.alibaba.fastjson.JSONObject;
import com.glaf.monitor.client.domain.FileSystemExt;
import com.glaf.monitor.client.util.SystemResourceJsonFactory;

public class SystemResource implements Serializable{
/**
 * 计算机用户系统
 */
   protected String userName;
   protected String computerName;
   
   /**
    * os信息
    */
   OperatingSystem os;
   /**
    * java 虚拟机版本信息
    */
   protected String jvmName;
   protected String jvmVersion;
   protected String javaVersion;
   protected String javaHome;
   /**
    * CPU
    */
   protected int cpuProcessors=0;//CPU核数
   protected String cpuVendor;//CPU提供商
   protected String cpuModel;//CPU类型
   protected double cpuMHZ;//单核频率
   
   /**
    * 内存
    */
   protected double memTotal;//总内存
   protected double memUsed;//使用内存
   protected double memFree;//剩余内存
   protected double swapTotal;//交换区总大小
   protected double swapUsed;//交换区使用
   protected double swapFree;//交换区剩余
   
   /**
    * 磁盘
    */
   List<FileSystemExt> fslist;
   
   protected String platform;//平台
   
   public JSONObject toJsonObject(){
	   return SystemResourceJsonFactory.toJsonObject(this);
   }

public String getUserName() {
	return userName;
}

public String getComputerName() {
	return computerName;
}

public String getJvmName() {
	return jvmName;
}

public String getJvmVersion() {
	return jvmVersion;
}


public int getCpuProcessors() {
	return cpuProcessors;
}

public String getCpuVendor() {
	return cpuVendor;
}

public String getCpuModel() {
	return cpuModel;
}

public double getCpuMHZ() {
	return cpuMHZ;
}

public double getMemTotal() {
	return memTotal;
}

public double getMemUsed() {
	return memUsed;
}

public double getMemFree() {
	return memFree;
}

public double getSwapTotal() {
	return swapTotal;
}

public double getSwapUsed() {
	return swapUsed;
}

public double getSwapFree() {
	return swapFree;
}

public List<FileSystemExt> getFslist() {
	return fslist;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public void setComputerName(String computerName) {
	this.computerName = computerName;
}

public void setJvmName(String jvmName) {
	this.jvmName = jvmName;
}

public void setJvmVersion(String jvmVersion) {
	this.jvmVersion = jvmVersion;
}

public void setCpuProcessors(int cpuProcessors) {
	this.cpuProcessors = cpuProcessors;
}

public void setCpuVendor(String cpuVendor) {
	this.cpuVendor = cpuVendor;
}

public void setCpuModel(String cpuModel) {
	this.cpuModel = cpuModel;
}

public void setCpuMHZ(double cpuMHZ) {
	this.cpuMHZ = cpuMHZ;
}

public void setMemTotal(double memTotal) {
	this.memTotal = memTotal;
}

public void setMemUsed(double memUsed) {
	this.memUsed = memUsed;
}

public void setMemFree(double memFree) {
	this.memFree = memFree;
}

public void setSwapTotal(double swapTotal) {
	this.swapTotal = swapTotal;
}

public void setSwapUsed(double swapUsed) {
	this.swapUsed = swapUsed;
}

public void setSwapFree(double swapFree) {
	this.swapFree = swapFree;
}

public void setFslist(List<FileSystemExt> fslist) {
	this.fslist = fslist;
}

public String getJavaVersion() {
	return javaVersion;
}

public String getJavaHome() {
	return javaHome;
}

public void setJavaVersion(String javaVersion) {
	this.javaVersion = javaVersion;
}

public void setJavaHome(String javaHome) {
	this.javaHome = javaHome;
}

public OperatingSystem getOs() {
	return os;
}

public void setOs(OperatingSystem os) {
	this.os = os;
}

public String getPlatform() {
	return platform;
}

public void setPlatform(String platform) {
	this.platform = platform;
}
}
