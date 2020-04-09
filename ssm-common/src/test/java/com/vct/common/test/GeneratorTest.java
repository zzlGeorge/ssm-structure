package com.vct.common.test;

import com.vct.common.generator.GeneratorMapper;
import com.vct.common.util.GenUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class GeneratorTest {

    public static void main(String[] args) throws IOException {
        // 加载mybatis相关对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);


        List<String> tables = Arrays.asList("van_user"); // 必填，需要你想生成的表信息
        List<String> templates = Arrays.asList(GenUtils.TemplateType.DOMAIN_JAVA, GenUtils.TemplateType.DAO_JAVA, GenUtils.TemplateType.MAPPER_XML); // 非必填，默认生成全模板
        String packageName = "com.vct.ssm"; // 非必填，具体包名，默认为配置中的包名
        String contextPath = "E:\\program\\ssm-structure\\ssm-demo"; // 非必填，生成文件上下文路径，默认为当前类文件上下文路径
        // 在项目中生成模板文件
        genSourceToProject(generatorMapper, tables, templates, packageName, contextPath);

        // 将生成的文件放入zip文件中输出
//        String outputPath = "C:\\Users\\lszhangzhangl\\Desktop\\demo.zip";
//        genZip(generatorMapper, tables, packageName, outputPath);
    }

    static void genSourceToProject(GeneratorMapper generatorMapper, List<String> tables,
                                   List<String> templates, String packageName, String contextPath) {
        List<String> tableNames = new ArrayList<String>(tables);
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = generatorMapper.get(tableName);
            //查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            //生成代码
//            GenUtils.generatorCodeToProject(table, columns,null, "com.vct.common");
            GenUtils.generatorCodeToProject(table, columns, templates, packageName, contextPath);
        }
    }

    static void genZip(GeneratorMapper generatorMapper, List<String> tables,
                       String packageName, String outputPath) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOs = new ZipOutputStream(outputStream);
        List<String> tableNames = new ArrayList<String>(tables);
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = generatorMapper.get(tableName);
            //查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            //生成代码
            GenUtils.generatorCodeToZip(table, columns, zipOs, packageName);
        }
        IOUtils.closeQuietly(zipOs);

        File file = new File(outputPath);
        if (!file.exists()) {
            boolean b = file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(outputStream.toByteArray());

        fos.close();
        outputStream.close();
    }

}
