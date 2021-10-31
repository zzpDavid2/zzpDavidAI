package zzpDavidAI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class ChatBot {
	
	private final int width = 600, height = 600;
	
	private static JFrame frame;
	
	private static JTextArea chat;
	
	private static JTextField input;
	
	private static JButton enter;
	private static JButton refresh;
	
	private static File tempFile;
	
	private static Path tempPath;
	
	private static String content;
	
	public ChatBot() throws IOException {
        tempFile = new File("ChatBotTemp.txt");
	    try {
	        tempFile.createNewFile();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    tempPath = tempFile.toPath();
	    
	    content ="The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\n\nHuman: Hello, who are you?\nAI: I am an AI created by OpenAI. How can I help you today?\nHuman: ";
	    
	    java.util.List<String> lines = Arrays.asList(content);
	    
	    Files.write(tempPath, lines , StandardCharsets.UTF_8);
		
		frame = new JFrame();
		
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		frame.setTitle("Chat Bot");
		
		chat = new JTextArea(content);
		
		chat.setEditable(false);
		
		input = new JTextField("");
		
		input.setEditable(true);
		
		enter = new JButton("Enter");
		
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				content = content + input.getText();
				
			    java.util.List<String> lines = Arrays.asList(content);
			    
//			    System.out.println(content);
			    
			    try {
					Files.write(tempPath, lines);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			    
			    chat.setText(content);
			    input.setText("");
			    
				frame.getContentPane().repaint();
				
//			    String userDirectory = new File("").getAbsolutePath();
//				
//				String command = "cmd.exe start python" +userDirectory+ "ChatBot.py";
//				
//				System.out.println(command);
				
				File python = new File("ChatBot.py");
				
				System.out.println(python.getAbsolutePath());
				
//				String[] cmd = {"python", python.getAbsolutePath()};
				try {
					ProcessBuilder pb = new ProcessBuilder("python3", python.getAbsolutePath()).inheritIO();
//					ProcessBuilder pb = new ProcessBuilder("open", "runP.sh");
//					pb.directory(new File(System.getProperty("user.home")));
					Process p = pb.start();
					int exitCode = p.waitFor();
					System.out.print(exitCode);
				} catch (InterruptedException | IOException e1) {
					e1.printStackTrace();
					System.out.println("Failed to started python");
				}
				
				try {
					content = Files.readString(tempPath, StandardCharsets.ISO_8859_1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			    chat.setText(content);
				
				frame.getContentPane().repaint();
			}		
		
		});
		
		refresh = new JButton("refresh");
		
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					content = Files.readString(tempPath, StandardCharsets.ISO_8859_1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			    chat.setText(content);
				
				frame.getContentPane().repaint();
			}
			
		});
		
		frame.getRootPane().setDefaultButton(enter);
		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(chat);
		
		frame.add(input);
		
		frame.add(enter);
		frame.add(refresh);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new ChatBot();
		String[] args1 = new String[] {"python3", "-m", "pip", "install", "--upgrade", "pip"};
		ProcessBuilder pb1 = new ProcessBuilder(args1).inheritIO();
		Process proc1 = pb1.start();
		proc1.waitFor();
		String[] args2 = new String[] {"python3", "-m", "pip", "install", "openai"};
		ProcessBuilder pb2 = new ProcessBuilder(args2).inheritIO();
		Process proc2 = pb2.start();
		proc2.waitFor();
		
	}
}
