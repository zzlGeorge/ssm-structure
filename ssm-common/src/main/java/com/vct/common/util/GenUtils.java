package com.vct.common.util;

import com.vct.common.entities.ColumnDO;
import com.vct.common.entities.TableDO;
import com.vct.common.Constants;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GenUtils {

    private static Logger logger = Logger.getLogger(GenUtils.class);

    private static final String propertiesPath = "generator.properties" ;

    public static final class TemplateType {
        public static String DOMAIN_JAVA = "templates/domain.java.vm" ;
        public static String DAO_JAVA = "templates/Dao.java.vm" ;
        public static String MAPPER_XML = "templates/Mapper.xml.vm" ;

        public static String CONTROLLER_JAVA = "templates/Controller.java.vm" ;
        public static String SERVICE_JAVA = "templates/Service.java.vm" ;
        public static String SERVICE_IMPL_JAVA = "templates/ServiceImpl.java.vm" ;

        public static String LIST_HTML = "templates/list.html.vm" ;
        public static String ADD_HTML = "templates/add.html.vm" ;
        public static String EDIT_HTML = "templates/edit.html.vm" ;
        public static String LIST_JS = "templates/list.js.vm" ;
        public static String ADD_JS = "templates/add.js.vm" ;
        public static String EDIT_JS = "templates/edit.js.vm" ;

        private static List<String> allTemplates;

        static {
            allTemplates = new ArrayList<>();
            allTemplates.add(DOMAIN_JAVA);
            allTemplates.add(DAO_JAVA);
            allTemplates.add(MAPPER_XML);
            allTemplates.add(SERVICE_JAVA);
            allTemplates.add(SERVICE_IMPL_JAVA);
            allTemplates.add(CONTROLLER_JAVA);
            allTemplates.add(LIST_HTML);
            allTemplates.add(ADD_HTML);
            allTemplates.add(EDIT_HTML);
            allTemplates.add(LIST_JS);
            allTemplates.add(ADD_JS);
            allTemplates.add(EDIT_JS);
        }
    }

    public static List<String> getTemplates() {
        return TemplateType.allTemplates;
    }

    /**
     * 生成的代码放入zip中
     *
     * @param table
     * @param columns
     * @param zip
     */
    public static void generatorCodeToZip(List<String> templates, Map<String, String> table, List<Map<String, String>> columns,
                                          ZipOutputStream zip, String packageName) {
        //配置信息
        Configuration config = getConfig();
        String cfgPackage = config.getString("package");
        if (StringUtils.isNotBlank(packageName) && !StringUtils.equals(packageName, cfgPackage)) {
            logger.warn("请注意，配置包名与传递过来的包名不一致，以实际传来的为准！");
            cfgPackage = packageName;
            config.setProperty("package", packageName);
        }

        TableDO tableDO = getTableDO(table, config, columns);

        VelocityContext context = getContext(tableDO, config);

        List<String> tpls = CollectionUtils.isEmpty(templates) ? getTemplates() : templates;
        for (String template : tpls) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(
                        getFileName(template, tableDO.getClassname(), tableDO.getClassName(), cfgPackage))
                );
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }

    }

    /**
     * @param table
     * @param columns
     * @param templates
     * @param packageName
     * @return
     */
    public static boolean generatorCodeToProject(Map<String, String> table,
                                                 List<Map<String, String>> columns,
                                                 List<String> templates,
                                                 String packageName) {
        return generatorCodeToProject(table, columns, templates, packageName, null);
    }

    /**
     * 生成的代码放入项目工程文件中
     *
     * @param table       表格信息
     * @param columns     列信息
     * @param templates   模板内容
     * @param packageName 包名
     * @param contextPath 指定上下文路径
     * @return
     */
    public static boolean generatorCodeToProject(Map<String, String> table,
                                                 List<Map<String, String>> columns,
                                                 List<String> templates,
                                                 String packageName,
                                                 String contextPath) {
        //配置信息
        Configuration config = getConfig();
        if (StringUtils.isNotBlank(packageName) && !StringUtils.equals(packageName, config.getString("package"))) {
            logger.warn("请注意，配置包名与传递过来的包名不一致，以实际传来的为准！");
            config.setProperty("package", packageName);
        }

        TableDO tableDO = getTableDO(table, config, columns);

        VelocityContext context = getContext(tableDO, config);

        // 获取模板
        List<String> temps = !CollectionUtils.isEmpty(templates) ? templates : getTemplates();

        for (String template : temps) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String fileName = getFileName(template, tableDO.getClassname(),
                        tableDO.getClassName(), packageName);
                fileName = (contextPath != null ? contextPath + File.separator : "") + fileName;
                FileUtil.createFile(fileName);
                if (StringUtils.isNotBlank(fileName)) {
                    File file = new File(fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    IOUtils.write(sw.toString(), fos, "UTF-8");
                    IOUtils.closeQuietly(sw);
                    fos.close();
                } else {
                    logger.warn("未获取文件路径！");
                }
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }

        return true;
    }

    /**
     * 获取表对象属性数据
     *
     * @param table
     * @param config
     * @param columns
     * @return
     */
    private static TableDO getTableDO(Map<String, String> table, Configuration config,
                                      List<Map<String, String>> columns) {
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"),
                config.getString("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnDO> columsList = new ArrayList<ColumnDO>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        return tableDO;
    }

    /**
     * 获取模板文件上下文
     *
     * @param tableDO
     * @param config
     * @return
     */
    private static VelocityContext getContext(TableDO tableDO, Configuration config) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", config.getString("package")
                .substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        return new VelocityContext(map);
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (Constants.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration(propertiesPath);
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className,
                                     String packageName) {
        String packagePath =
                "src" + File.separator + "main" + File.separator + "java" + File.separator;

        String moduleName = packageName.substring(packageName.lastIndexOf(".") + 1);

        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("swaggerModel.java.vm")) {
            return packagePath + "model" + File.separator + className + "Model.java" ;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + "domain" + File.separator + className + "DO.java";
        }

        if (template.contains("entity.java.vm")) {
            return packagePath + "dao" + File.separator + "entity" + File.separator + className + ".java" ;
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java" ;
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "dao" + File.separator + "mapper" + File.separator + className + "Mapper.java" ;
        }

        if (template.contains("Mapper.xml.vm")) {
//            return packagePath + "dao" + File.separator + className + "Dao.xml";
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "mybatis" + File.separator + className + "Dao.xml" ;
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + "I" + className + "Service.java" ;
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className
                    + "ServiceImpl.java" ;
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java" ;
        }

        if (template.contains("Api.java.vm")) {
            return packagePath + "controller" + File.separator + "api" + File.separator + className + "Api.java" ;
        }

        if (template.contains("list.html.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "templates" + File.separator + moduleName + File.separator + classname
                    + File.separator + classname + ".html" ;
            //				+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }
        if (template.contains("add.html.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "templates" + File.separator + moduleName + File.separator + classname
                    + File.separator + "add.html" ;
        }
        if (template.contains("edit.html.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "templates" + File.separator + moduleName + File.separator + classname
                    + File.separator + "edit.html" ;
        }

        if (template.contains("list.js.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "static" + File.separator + "js" + File.separator + "appjs" + File.separator
                    + moduleName + File.separator + classname + File.separator + classname + ".js" ;
            //		+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }
        if (template.contains("add.js.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "static" + File.separator + "js" + File.separator + "appjs" + File.separator
                    + moduleName + File.separator + classname + File.separator + "add.js" ;
        }
        if (template.contains("edit.js.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator
                    + "static" + File.separator + "js" + File.separator + "appjs" + File.separator
                    + moduleName + File.separator + classname + File.separator + "edit.js" ;
        }
        throw new RuntimeException("异常！无法识别的模板！");
    }
}
