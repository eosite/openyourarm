package com.glaf.generator;

import java.io.*;
import java.util.*;

import freemarker.cache.*;
import freemarker.template.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.glaf.generator.xml.XmlReader;
import com.glaf.core.domain.*;
import com.glaf.core.el.*;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.ZipUtils;

public class JavaCodeGen {
	protected static Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

	private final static String DEFAULT_CONFIG = "templates/codegen/codegen_all.xml";

	private static Log logger = LogFactory.getLog(JavaCodeGen.class);

	public final static String newline = System.getProperty("line.separator");

	static {
		try {
			File baseDir = new File(".");
			FileTemplateLoader ftl = new FileTemplateLoader(baseDir, true);
			ClassTemplateLoader ctl = new ClassTemplateLoader(
					JavaCodeGen.class.getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			cfg.setTemplateLoader(mtl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	public static void main(String[] args) throws Exception {
		FileInputStream fin = new FileInputStream(args[0]);
		TableDefinition def = new com.glaf.core.xml.XmlReader().read(fin);
		JavaCodeGen gen = new JavaCodeGen();
		File outputDir = new File(args[1]);
		String config = JavaCodeGen.DEFAULT_CONFIG;
		if (args.length > 2) {
			config = args[2];
		}
		System.out.println(outputDir);
		System.out.println(config);
		gen.codeGen(def, outputDir, config);
	}

	public List<CodeDef> codeGen(TableDefinition tableDefinition,
			java.io.File outputDir) throws Exception {
		return this.codeGen(tableDefinition, outputDir, DEFAULT_CONFIG);
	}

	public List<CodeDef> codeGen(TableDefinition tableDefinition,
			java.io.File outputDir, String config) throws Exception {
		if (tableDefinition.getIdField() == null) {
			ColumnDefinition idField = new ColumnDefinition();
			idField.setName("id");
			idField.setColumnName("ID_");
			idField.setType("Long");
			tableDefinition.setIdField(idField);
		}
		List<CodeDef> defs = new ArrayList<CodeDef>();
		Map<String, Object> context = new HashMap<String, Object>();

		String entityName = tableDefinition.getEntityName();
		entityName = StringTools.upper(entityName);
		String modelName = StringTools.lower(entityName);
		String packageName = tableDefinition.getPackageName();

		context.put("tableDefinition", tableDefinition);
		context.put("tableDefinition", tableDefinition);
		context.put("idField", tableDefinition.getIdField());
		context.put("packageName", packageName);
		context.put("packagePath", StringTools.replace(packageName, ".", "/"));
		context.put("entityName", entityName);
		context.put("modelName", modelName);
		context.put("className", tableDefinition.getClassName());
		context.put("tableName", tableDefinition.getTableName().toUpperCase());
		context.put("newline", newline);
		context.put("blank4", "    ");
		context.put("blank8", "        ");
		context.put("blank16", "                ");
		context.put("blank32", "                                ");

		logger.debug(context);

		if (tableDefinition.isJbpmSupport()) {
			ColumnDefinition f0 = new ColumnDefinition();
			f0.setName("status");
			f0.setColumnName("STATUS_");
			f0.setEnglishTitle("status");
			f0.setTitle("业务状态");
			f0.setType("Integer");
			tableDefinition.addField(f0);

			ColumnDefinition f1 = new ColumnDefinition();
			f1.setName("processName");
			f1.setColumnName("PROCESSNAME_");
			f1.setEnglishTitle("processName");
			f1.setTitle("流程名称");
			f1.setLength(100);
			f1.setType("String");
			tableDefinition.addField(f1);

			ColumnDefinition f2 = new ColumnDefinition();
			f2.setName("processInstanceId");
			f2.setColumnName("PROCESSINSTANCEID_");
			f2.setEnglishTitle("processInstanceId");
			f2.setTitle("流程实例编号");
			f2.setType("Long");
			tableDefinition.addField(f2);

			ColumnDefinition f3 = new ColumnDefinition();
			f3.setName("wfStatus");
			f3.setColumnName("WFSTATUS_");
			f3.setEnglishTitle("wfStatus");
			f3.setTitle("工作流状态");
			f3.setType("Integer");
			tableDefinition.addField(f3);

			ColumnDefinition f4 = new ColumnDefinition();
			f4.setName("wfStartDate");
			f4.setColumnName("WFSTARTDATE_");
			f4.setEnglishTitle("wfStartDate");
			f4.setTitle("工作流启动日期");
			f4.setType("Date");
			tableDefinition.addField(f4);

			ColumnDefinition f5 = new ColumnDefinition();
			f5.setName("wfEndDate");
			f5.setColumnName("WFENDDATE_");
			f5.setEnglishTitle("wfEndDate");
			f5.setTitle("工作流结束日期");
			f5.setType("Date");
			tableDefinition.addField(f5);

		}

		if (tableDefinition.isTreeSupport()) {
			ColumnDefinition idField = new ColumnDefinition();
			idField.setName("id");
			idField.setColumnName("ID_");
			idField.setEnglishTitle("Id");
			idField.setTitle("主键");
			idField.setType("Long");
			tableDefinition.setIdField(idField);

			ColumnDefinition f1 = new ColumnDefinition();
			f1.setName("parentId");
			f1.setColumnName("PARENTID_");
			f1.setEnglishTitle("parentId");
			f1.setTitle("父节点编号");
			f1.setType("Long");
			tableDefinition.addField(f1);

			ColumnDefinition f2 = new ColumnDefinition();
			f2.setName("code");
			f2.setColumnName("CODE_");
			f2.setEnglishTitle("Code");
			f2.setTitle("编码");
			f2.setType("String");
			tableDefinition.addField(f2);

			ColumnDefinition f7 = new ColumnDefinition();
			f7.setName("discriminator");
			f7.setColumnName("DISCRIMINATOR_");
			f7.setEnglishTitle("discriminator");
			f7.setTitle("标识符");
			f7.setType("String");
			tableDefinition.addField(f7);

			ColumnDefinition f8 = new ColumnDefinition();
			f8.setName("description");
			f8.setColumnName("DESCRIPTION_");
			f8.setEnglishTitle("description");
			f8.setTitle("描述");
			f8.setType("String");
			tableDefinition.addField(f8);

			ColumnDefinition f9 = new ColumnDefinition();
			f9.setName("icon");
			f9.setColumnName("ICON_");
			f9.setEnglishTitle("icon");
			f9.setTitle("图标");
			f9.setType("String");
			tableDefinition.addField(f9);

			ColumnDefinition f92 = new ColumnDefinition();
			f92.setName("iconCls");
			f92.setColumnName("ICONCLS_");
			f92.setEnglishTitle("iconCls");
			f92.setTitle("图标样式");
			f92.setType("String");
			tableDefinition.addField(f92);

			ColumnDefinition f10 = new ColumnDefinition();
			f10.setName("locked");
			f10.setColumnName("LOCKED_");
			f10.setEnglishTitle("locked");
			f10.setTitle("锁定标识");
			f10.setType("Integer");
			tableDefinition.addField(f10);

			ColumnDefinition f11 = new ColumnDefinition();
			f11.setName("sortNo");
			f11.setColumnName("SORTNO_");
			f11.setEnglishTitle("sortNo");
			f11.setTitle("顺序号");
			f11.setType("Integer");
			tableDefinition.addField(f11);

			ColumnDefinition f12 = new ColumnDefinition();
			f12.setName("name");
			f12.setColumnName("NAME_");
			f12.setEnglishTitle("name");
			f12.setTitle("名称");
			f12.setType("String");
			tableDefinition.addField(f12);

			ColumnDefinition f13 = new ColumnDefinition();
			f13.setName("treeId");
			f13.setColumnName("TREEID_");
			f13.setEnglishTitle("treeId");
			f13.setTitle("树编号");
			f13.setType("String");
			tableDefinition.addField(f13);

			ColumnDefinition f14 = new ColumnDefinition();
			f14.setName("url");
			f14.setColumnName("URL_");
			f14.setEnglishTitle("url");
			f14.setTitle("链接地址");
			f14.setType("String");
			tableDefinition.addField(f14);

		}

		ColumnDefinition f3 = new ColumnDefinition();
		f3.setName("createBy");
		f3.setColumnName("CREATEBY_");
		f3.setEnglishTitle("createBy");
		f3.setTitle("创建人");
		f3.setType("String");
		f3.setLength(50);
		// tableDefinition.addField(f3);

		ColumnDefinition f4 = new ColumnDefinition();
		f4.setName("createDate");
		f4.setColumnName("CREATEDATE_");
		f4.setEnglishTitle("createDate");
		f4.setTitle("创建日期");
		f4.setType("Date");
		// tableDefinition.addField(f4);

		ColumnDefinition f5 = new ColumnDefinition();
		f5.setName("updateDate");
		f5.setColumnName("UPDATEDATE_");
		f5.setEnglishTitle("updateDate");
		f5.setTitle("修改日期");
		f5.setType("Date");
		// tableDefinition.addField(f5);

		ColumnDefinition f6 = new ColumnDefinition();
		f6.setName("updateBy");
		f6.setColumnName("UPDATEBY_");
		f6.setEnglishTitle("updateBy");
		f6.setTitle("修改人");
		f6.setType("String");
		f6.setLength(50);
		// tableDefinition.addField(f6);

		Map<String, ColumnDefinition> fields = tableDefinition.getFields();
		List<ColumnDefinition> values = new ArrayList<ColumnDefinition>();
		Iterator<ColumnDefinition> iter = fields.values().iterator();
		while (iter.hasNext()) {
			ColumnDefinition field = iter.next();
			if (tableDefinition.getIdField() != null
					&& StringUtils.equalsIgnoreCase(tableDefinition
							.getIdField().getColumnName(), field
							.getColumnName())) {
				continue;
			}
			values.add(field);
		}

		context.put("pojo_fields", values);

		StringBuffer b04 = new StringBuffer();
		StringBuffer b05 = new StringBuffer();

		int displaySize = 0;
		for (ColumnDefinition field : fields.values()) {
			if (field.getDisplayType() == 4) {
				displaySize++;
			}

			if (StringUtils.equalsIgnoreCase(field.getType(), "Date")) {

				b04.append(newline).append(newline)
						.append("		if(paramMap.get(\"").append(field.getName())
						.append("\") != null ){");
				b04.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b04.append(newline).append("		}");

				b05.append(newline).append(newline).append("		if(")
						.append(modelName).append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("() != null ){");
				b05.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b05.append(newline).append("		}");

			} else if (StringUtils.equalsIgnoreCase(field.getType(), "Integer")) {
				if (StringUtils.equals("locked", field.getName())
						|| StringUtils.equals("deleteFlag", field.getName())
						|| StringUtils.equals("status", field.getName())
						|| StringUtils.equals("wfStatus", field.getName())) {
					b04.append(newline).append(newline)
							.append("		if(paramMap.get(\"")
							.append(field.getName()).append("\") != null ){");
					b04.append(newline).append("			paramMap.put(\"")
							.append(field.getName()).append("Equals\", ")
							.append(modelName).append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("());");
					b04.append(newline).append("		}");
					b05.append(newline).append(newline).append("		if(")
							.append(modelName).append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("() != null ){");
					b05.append(newline).append("			paramMap.put(\"")
							.append(field.getName()).append("Equals\", ")
							.append(modelName).append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("());");
					b05.append(newline).append("		}");
				} else {
					b04.append(newline).append(newline)
							.append("		if(paramMap.get(\"")
							.append(field.getName()).append("\") != null ){");
					b04.append(newline).append("			paramMap.put(\"")
							.append(field.getName())
							.append("GreaterThanOrEqual\", ").append(modelName)
							.append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("());");
					b04.append(newline).append("		}");
					b05.append(newline).append(newline).append("		if(")
							.append(modelName).append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("() != null ){");
					b05.append(newline).append("			paramMap.put(\"")
							.append(field.getName())
							.append("GreaterThanOrEqual\", ").append(modelName)
							.append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("());");
					b05.append(newline).append("		}");
				}
			} else if (StringUtils.equalsIgnoreCase(field.getType(), "Long")) {
				b04.append(newline).append(newline)
						.append("		if(paramMap.get(\"").append(field.getName())
						.append("\") != null ){");
				b04.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b04.append(newline).append("		}");
				b05.append(newline).append(newline).append("		if(")
						.append(modelName).append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("() != null ){");
				b05.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b05.append(newline).append("		}");
			} else if (StringUtils.equalsIgnoreCase(field.getType(), "Double")) {
				b04.append(newline).append(newline)
						.append("		if(paramMap.get(\"").append(field.getName())
						.append("\") != null ){");
				b04.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b04.append(newline).append("		}");
				b05.append(newline).append(newline).append("		if(")
						.append(modelName).append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("() != null ){");
				b05.append(newline).append("			paramMap.put(\"")
						.append(field.getName())
						.append("GreaterThanOrEqual\", ").append(modelName)
						.append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b05.append(newline).append("		}");
			} else if (StringUtils.equalsIgnoreCase(field.getType(), "Boolean")) {
				b04.append(newline).append(newline)
						.append("		if(paramMap.get(\"").append(field.getName())
						.append("\") != null ){");
				b04.append(newline).append("			paramMap.put(\"")
						.append(field.getName()).append("\", ")
						.append(modelName).append("Query.get")
						.append(StringTools.upper(field.getName()))
						.append("());");
				b04.append(newline).append("		}");
			} else {
				if (StringUtils.isNotEmpty(field.getColumnName())) {
					b04.append(newline).append(newline)
							.append("		if(paramMap.get(\"")
							.append(field.getName()).append("\") != null ){");
					if (field.getDataCode() == null) {
						b04.append(newline).append("			paramMap.put(\"")
								.append(field.getName())
								.append("Like\", \"%\"+").append(modelName)
								.append("Query.get")
								.append(StringTools.upper(field.getName()))
								.append("()+\"%\");");
					}
					b04.append(newline).append("		}");
					b05.append(newline).append(newline).append("		if(")
							.append(modelName).append("Query.get")
							.append(StringTools.upper(field.getName()))
							.append("() != null ){");
					if (field.getDataCode() == null) {
						b05.append(newline).append("			paramMap.put(\"")
								.append(field.getName())
								.append("Like\", \"%\"+").append(modelName)
								.append("Query.get")
								.append(StringTools.upper(field.getName()))
								.append("()+\"%\");");
					}
					b05.append(newline).append("		}");
				}
			}
		}

		context.put("query_params", b04.toString());
		context.put("query_paramMap", b05.toString());
		context.put("displaySize", displaySize);
		context.put("jbpmSupport", tableDefinition.isJbpmSupport());
		context.put("tableDefinition", tableDefinition);

		String configLocation = config;

		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG;
		}

		InputStream inputStream = null;
		Resource resource = null;
		try {
			resource = new ClassPathResource(configLocation);
			inputStream = resource.getInputStream();
		} catch (Exception ex) {
			resource = new FileSystemResource(configLocation);
			inputStream = resource.getInputStream();
		}

		XmlReader reader = new XmlReader();
		List<CodeDef> rows = reader.read(inputStream);
		if (rows != null && !rows.isEmpty()) {
			for (CodeDef def : rows) {
				String expression = def.getExpression();
				if (StringUtils.isNotEmpty(expression)) {
					Object value = Mvel2ExpressionEvaluator.evaluate(
							expression, context);
					if (value != null) {
						if (value instanceof Boolean) {
							Boolean b = (Boolean) value;
							if (!b.booleanValue()) {
								continue;
							}
						}
					}
				}
				String tpl = def.getTemplate();
				String processor = def.getProcessor();
				if (processor != null) {
					Class<?> c = ClassUtils.loadClass(processor);
					Object object = c.newInstance();
					if (object instanceof CodeGenerator) {
						CodeGenerator p = (CodeGenerator) object;
						String content = p.process(tableDefinition, context);
						content = StringTools.replace(content, "#JSF{", "#{");
						content = StringTools.replaceIgnoreCase(content, "#F{",
								"${");
						content = StringTools.replaceIgnoreCase(content, "$F{",
								"${");
						def.setContent(content);
					}
				} else {
					StringWriter writer = new StringWriter();
					try {
						Template temp = cfg.getTemplate(tpl);
						temp.process(context, writer);
						writer.flush();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					String content = writer.toString();
					content = StringTools.replace(content, "#JSF{", "#{");
					content = StringTools.replace(content, "#GG{", "#{");
					content = StringTools.replaceIgnoreCase(content, "#F{",
							"${");
					content = StringTools.replaceIgnoreCase(content, "$F{",
							"${");
					def.setContent(content);
				}

				String content = def.getContent();
				String saveName = def.getSaveName();
				saveName = ExpressionTools.evaluate(saveName, context);
				def.setSaveName(saveName);
				if (content != null && outputDir != null) {
					String path = outputDir.getAbsolutePath() + "/"
							+ def.getSavePath();
					String filename = path + "/" + saveName;
					FileUtils.mkdirs(path);
					if (def.getEncoding() != null) {
						logger.debug(def.getEncoding());
						FileUtils.save(filename,
								content.getBytes(def.getEncoding()));
					} else {
						FileUtils.save(filename, content.getBytes());
					}
				}

				defs.add(def);
			}
		}

		return defs;
	}

	public List<CodeDef> codeGen(TableDefinition tableDefinition, String config)
			throws Exception {
		return this.codeGen(tableDefinition, null, config);
	}

	public byte[] zipCodeGen(TableDefinition tableDefinition, String config)
			throws Exception {
		byte[] bytes = null;
		Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
		List<CodeDef> rows = this.codeGen(tableDefinition, null, config);
		for (CodeDef def : rows) {
			String content = def.getContent();
			String savePath = def.getSavePath();
			String saveName = def.getSaveName();
			String filename = savePath + "/" + saveName;
			if (filename.startsWith("/") || filename.startsWith("\\")) {
				filename = filename.substring(1, filename.length());
			}
			zipMap.put(filename, content.getBytes());
		}
		bytes = ZipUtils.toZipBytes(zipMap);
		return bytes;
	}

}
