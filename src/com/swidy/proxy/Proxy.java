package com.swidy.proxy;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {

	public static Object newProxyInstance(Class infce, InvocationHandler h) throws Exception{
		String methodStr = "";
		String rt = "\r\t";
		
		Method[] methods = infce.getMethods();
		for(Method m : methods) {
			methodStr += "@Override" + rt + 
					 "public void " + m.getName() + "() {" + rt +
					 "    try {" + rt +
					 "    Method md = " + infce.getName() + ".class.getMethod(\"" + m.getName() + "\");" + rt +
					 "    h.invoke(this, md);" + rt +
					 "    }catch(Exception e) {e.printStackTrace();}" + rt +
					
					 "}";
		}
		
		String src = 
				"package com.swidy.proxy;" +  rt +
				"import java.lang.reflect.Method;" + rt +
				"public class $Proxy1 implements " + infce.getName() + "{" + rt +
				"    public $Proxy1(InvocationHandler h) {" + rt +
				"        this.h = h;" + rt +
				"    }" + rt +
				
				
				"    com.swidy.proxy.InvocationHandler h;" + rt +
								
				methodStr +
				"}";
		
		String fileName = "d:/src/com/swidy/proxy/$Proxy1.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(src);
		fw.flush();
		fw.close();
		
		//compile
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);
		CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
		t.call();
		fileMgr.close();
		
		//load into memory and create an instance
		URL[] urls = new URL[] {new URL("file:/" + "d:/src/")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("com.swidy.proxy.$Proxy1");
		System.out.println("代理类：" + c);
		
		Constructor ctr = c.getConstructor(InvocationHandler.class);
		Object m = ctr.newInstance(h);
		return m;
	}
}
