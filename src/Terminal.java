import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Terminal {
    private Parser parser;

    public Terminal(Parser parser) {
        this.parser = parser;
    }

    public void echo(){
        for(int i = 0;i<parser.getArgs().length;i++){
            System.out.print(parser.getArgs()[i] + ' ');
        }
        System.out.println();
    }
    public void pwd(){
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
    }
    public void cd(){

    }
    public void ls(){
        File currDir = new File(".");
        File[] files = currDir.listFiles();
        Arrays.sort(files);
        for(File f : files){
            if(f.isDirectory() || f.isFile())
                System.out.print(f.getName() + "    ");
        }
        System.out.println();
    }
    public void ls_r(){
        File currDir = new File(".");
        File[] files = currDir.listFiles();
        Arrays.sort(files, Collections.reverseOrder());
        for(File f : files){
            if(f.isDirectory())
                System.out.print(f.getName() + "    ");
            else if(f.isFile()){
                System.out.print(f.getName() + "    ");
            }
        }
        System.out.println();
    }


    public void mkdir(){

    }
    public void rmdir(){

    }
    public void cp(){

    }
    public void cp_r(){

    }


    public void touch(){

    }
    public void rm(){

    }
    public void cat(){

    }
    public void wc(){

    }


    public void chooseCommandAction(){
        String choice = parser.getCommandName();
        switch (choice){
            case "echo":
                echo();
                break;
            case "pwd":
                pwd();
                break;
            case "cd":
                cd();
                break;
            case "ls":
                ls();
                break;
            case "ls -r":
                ls_r();
                break;
            case "mkdir":
                mkdir();
                break;
            case "rmdir":
                rmdir();
                break;
            case "cp":
                cp();
                break;
            case "cp -r":
                cp_r();
                break;
            case "touch":
                touch();
                break;
            case "rm":
                rm();
                break;
            case "cat":
                cat();
                break;
            case "wc":
                wc();
                break;
            default:
                System.out.println("Error: Command not found or invalid parameters are entered!");
        }
    }
    public static void main(String[] args){
        while(true) {
            Terminal t = new Terminal(new Parser());
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if (input.equals("exit"))
                break;
            t.parser.parse(input);
            t.chooseCommandAction();
        }
    }
}