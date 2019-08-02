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

//        genZip(generatorMapper);
        genSourceToProject(generatorMapper);
    }

    static void genSourceToProject(GeneratorMapper generatorMapper){
        List<String> tableNames = new ArrayList<String>(Arrays.asList("tb_user"));
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = generatorMapper.get(tableName);
            //查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            //生成代码
            GenUtils.generatorCodeToProject(table, columns,null, "com.vct.common");
        }
    }

    static void genZip(GeneratorMapper generatorMapper) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOs = new ZipOutputStream(outputStream);
        List<String> tableNames = new ArrayList<String>(Arrays.asList("tb_user"));
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = generatorMapper.get(tableName);
            //查询列信息
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            //生成代码
            GenUtils.generatorCodeToZip(table, columns, zipOs);
        }
        IOUtils.closeQuietly(zipOs);

        File file = new File("C:/", "Users/lszhangzhangl/Desktop/demo.zip");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(outputStream.toByteArray());

        fos.close();
        outputStream.close();
    }

}
