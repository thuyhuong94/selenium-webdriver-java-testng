package JavaTester;

public class Topic_03_System_Properties {

	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);

	}

}
