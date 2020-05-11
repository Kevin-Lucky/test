package com.pingan;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest(classes = SpringbootFileApplication.class)
@RunWith(SpringRunner.class)
class SpringbootFileApplicationTests {

	@Test
	public void test() throws URISyntaxException {
		System.out.println("java.home : "+System.getProperty("java.home"));

		System.out.println("java.class.version : "+System.getProperty("java.class.version"));

		System.out.println("java.class.path : "+System.getProperty("java.class.path"));

		System.out.println("java.library.path : "+System.getProperty("java.library.path"));

		System.out.println("java.io.tmpdir : "+System.getProperty("java.io.tmpdir"));

		System.out.println("java.compiler : "+System.getProperty("java.compiler"));

		System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));

		System.out.println("user.name : "+System.getProperty("user.name"));

		System.out.println("user.home : "+System.getProperty("user.home"));

		System.out.println("user.dir : "+System.getProperty("user.dir"));

		System.out.println("===================");

		System.out.println("package: "+Test.class.getPackage().getName());

		System.out.println("package: "+Test.class.getPackage().toString());

		System.out.println("=========================");

		String packName = Test.class.getPackage().getName();

                /*URL packurl = new URL(packName);
                System.out.println(packurl.getPath());*/

		URI packuri = new URI(packName);

		System.out.println(packuri.getPath());

		//System.out.println(packuri.toURL().getPath());

		System.out.println(packName.replaceAll("//.", "/"));

		System.out.println(System.getProperty("user.dir")+"/"+(Test.class.getPackage().getName()).replaceAll("//.", "/")+"/");

	}

	@Test
	public void t() throws IOException {
		ClassPathResource resource =new ClassPathResource("/excelTemplates");
        InputStream inputStream = resource.getInputStream();

//        String s = IOUtils.toString(inputStream);
//        StringWriter writer = new StringWriter();
//        IOUtils.copy(inputStream, writer, "utf-8");
//        inputStream.close();
//        System.out.println(writer);

        List<String> list = IOUtils.readLines(inputStream);
        String fileName = list.get(0);
        inputStream.close();
        System.out.println(fileName);

    }

}
