import java.io.File;
import java.io.IOException;
import java.util.*;

public class Terminal {
    private Parser parser;
    private String path;
    private List<String> usedCommands = new ArrayList<String>();
    public Terminal(Parser parser) {
        this.parser = parser;
        this.path = System.getProperty("user.home");
    }

    public void echo(){
        for(int i = 0;i<parser.getArgs().length;i++){
            System.out.print(parser.getArgs()[i] + ' ');
        }
        System.out.println();
    }
    public void pwd(){
        System.out.println(path);
    }
    public void cd(){

    }
    public void ls(){
        File currDir = new File(path);
        File[] files = currDir.listFiles();
        Arrays.sort(files);
        for(File f : files){
            if(f.isDirectory() || f.isFile())
                System.out.print(f.getName() + "    ");
        }
        System.out.println();
    }
    public void ls_r(){
        File currDir = new File(path);
        File[] files = currDir.listFiles();
        Arrays.sort(files, Collections.reverseOrder());
        for(File f : files){
            if(f.isDirectory() || f.isFile())
                System.out.print(f.getName() + "    ");
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
        File newFile = new File(parser.getArgs()[0]);
        try{
            newFile.createNewFile();
        }
        catch(IOException e){
            System.out.println("Error: No such directory exists!");
        }
    }
    public void history(){
        for(int i = 0;i< usedCommands.size();i++){
            System.out.println(i + 1 + "  " + usedCommands.get(i));
        }
    }
    public void rm(){

    }
    public void cat(){

    }
    public void wc(){

    }
    public void greaterThanOperator(){

    }
    public void greaterThanOperator2(){

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
            case "history":
                history();
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
            case ">":
                greaterThanOperator();
                break;
            case ">>":
                greaterThanOperator2();
                break;
            default:
                System.out.println("Error: Command not found or invalid parameters are entered!");
        }
    }
    public static void main(String[] args){
        Terminal t = new Terminal(new Parser());
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print(">");
            String input = scan.nextLine();
            if (input.equals("exit"))
                break;
            t.parser.parse(input);
            t.usedCommands.add(t.parser.getCommandName());
            t.chooseCommandAction();
        }
    }
}