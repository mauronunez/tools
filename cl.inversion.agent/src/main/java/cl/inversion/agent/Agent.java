package cl.inversion.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
	
	public static void premain(String agentArgs,Instrumentation inst){
		
		ClassFileTransformer transformer=new ClassFileTransformer() {
			
			public byte[] transform(ClassLoader loader, String className,
					Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
					byte[] classfileBuffer) throws IllegalClassFormatException {
				System.out.println("[cl.inversion]loading:"+className);
				return null;
			}
		};
		inst.addTransformer(transformer,true);
		
	}

}
