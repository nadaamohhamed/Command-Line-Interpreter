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
        if(parser.getArgs().length == 0){
            this.path = System.getProperty("user.home");
        }else if(parser.getArgs()[0].equals("..")){
            if(path.charAt(path.length()-1) != '\\') {
                int ind = 0;
                for (int i = path.length() - 1; i >= 0 ; i--){
                    if(path.charAt(i) == '\\') {
                        ind = i;
                        break;
                    }
                }
                if(path.charAt(ind-1) != ':') path = path.substring(0,ind);
                else path = path.substring(0,ind+1);
            }
        }else{
            String nwPath = "";
            for(int i = 0 ; i < parser.getArgs().length ; i++){
                nwPath+= parser.getArgs()[i] +" ";
            }
            if(nwPath.charAt(0) =='\\' || (nwPath.length()>= 3 && nwPath.charAt(1) == 'C' && nwPath.charAt(1) == ':' && nwPath.charAt(2) == '\\')){
                if(nwPath.charAt(0) =='\\'){
                    nwPath = "C:" + nwPath ;
                }
            }else{
                if(path.charAt(path.length()-1)!='\\')
                    nwPath = path+'\\'+nwPath;
            }
            if(nwPath.charAt(nwPath.length()-1) == ' ' ) nwPath = nwPath.substring(0,nwPath.length()-1);
            if(nwPath.charAt(nwPath.length()-1) == '\\' && nwPath.charAt(nwPath.length()-2) !=':') nwPath = nwPath.substring(0,nwPath.length()-1);
            File dir = new File(nwPath);
            if(dir.isDirectory()) path = nwPath;
            else System.out.println("Cannot find path '"+nwPath+"' because it does not exist.");
        }
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
        String[] args = parser.getArgs();
        for(int i=0; i < args.length;i++){
            String currPath = args[i];
            if(currPath.charAt(0) =='\\' || (currPath.length()>= 3 && currPath.charAt(0) == 'C' && currPath.charAt(1) == ':' && currPath.charAt(2) == '\\')){
                if(currPath.charAt(0) =='\\'){
                    currPath = "C:" + currPath;
                }
                File f1=new File(currPath);
                f1.mkdir();
            }
            else{
                File f1=new File(path+"\\"+currPath);
                f1.mkdir();
            }
        }
    }
    public void rmdir(){
        String[] args = parser.getArgs();
        for(int i=0; i < args.length;i++){
            String currPath = args[i];
            if(currPath.contains("*")){
                File currDir = new File(path);
                File[] files = currDir.listFiles();
                for(File f : files){
                    f.delete();
                }
            }else{
                if(currPath.charAt(0) =='\\' || (currPath.length()>= 3 && currPath.charAt(0) == 'C' && currPath.charAt(1) == ':' && currPath.charAt(2) == '\\')){
                    if(currPath.charAt(0) =='\\'){
                        currPath = "C:" + currPath;
                    }
                    File f1=new File(currPath);
                    f1.delete();
                }
            }
        }
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
        String name =  parser.getArgs()[0];
        if(path.charAt(path.length()-1) != '\\')  name = '\\' + name;
        File file = new File(path+name);
        if(file.exists()){
            file.delete();
        }
        System.out.println();
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
            System.out.print(t.path+">");
            String input = scan.nextLine();
            if (input.equals("exit"))
                break;
            t.parser.parse(input);
            t.usedCommands.add(t.parser.getCommandName());
            t.chooseCommandAction();
        }
    }
}