import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

class Parser {
    private String commandName;
    private String[] args;

    public void parse(String input){
        commandName = "";
        String[] subStrings = input.split(" ");
        commandName += subStrings[0];
        int sz = subStrings.length - 1;

        if(subStrings.length > 1) {
            int i = 1, j = 0;
            if (subStrings[i].charAt(0) == '-') {
                commandName += (' ' + subStrings[i++]);
                sz--;
            }
            args = new String[sz];
            for (; i < subStrings.length; i++) {
                args[j++] = subStrings[i];
            }
        }
        else
            args = new String[sz];
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }

    public void removeLast2(){
        String nwArgs[] = new String[args.length-2];
        for(int i = 0 ; i < args.length-2 ; i++){
            nwArgs[i] = args[i];
        }
        args = nwArgs;
    }
}
class Terminal {
    private Parser parser;
    private String path;
    private boolean error;
    private List<String> usedCommands = new ArrayList<String>();
    public Terminal(Parser parser) {
        this.parser = parser;
        this.path = System.getProperty("user.home");
        this.error = false;
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
                //case of path = "c:\"
                if(path.charAt(path.length()-1)!='\\')
                    nwPath = path+'\\'+nwPath;
                else
                    nwPath = path + nwPath;
            }
            // case of nwPath contain extra space = "c:\"
            if(nwPath.charAt(nwPath.length()-1) == ' ' ) nwPath = nwPath.substring(0,nwPath.length()-1);

            // case of nwPath contain extra backslash
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
                    if(currPath.charAt(0) !='\\'){
                        currPath = "C:" + currPath;
                    }

                }else{
                    currPath  = path + '\\' + currPath;
                }
                File f1=new File(currPath);
                if(!f1.delete()){
                    System.out.println(currPath + " " + "is not empty!");
                }
            }
        }
    }
    public void cp(){
        String[] args = parser.getArgs();
        String firstFile=args[0];
        String secondFile=args[1];
        File oriFile = new File(path+"\\"+firstFile);
        File newFile = new File(path+"\\"+secondFile);
        try{
            Files.copy(oriFile.toPath(),newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){
            System.out.println("Error");
        }

    }
    public void cp_r() {
        String[] args = parser.getArgs();
        String firstFile=args[0];
        String secondFile=args[1];
        String sourceDirectory  , targetDirectory;
        if(path.charAt(path.length()-1)== '\\'){
            sourceDirectory = path +  firstFile;
            targetDirectory = path +  secondFile + "\\" + firstFile;
        }else
        {
            sourceDirectory = path + "\\" + firstFile;
            targetDirectory = path + "\\" + secondFile + "\\" + firstFile;
        }

        try{
            Files.walk(Paths.get(sourceDirectory))
                    .forEach(source -> {
                        Path destination = Paths.get(targetDirectory, source.toString()
                                .substring(sourceDirectory.length()));
                        System.out.println(source);
                        System.out.println(destination);
                        try {
                            Files.copy(source, destination);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }
    public void touch(){
        String filePath = "";
        // No path given - file name only
        if(!parser.getArgs()[0].contains("\\")){
            filePath = path + '\\' + parser.getArgs()[0];
        }
        // Relative path
        else if(parser.getArgs()[0].charAt(0) != '\\'){
            filePath = path + '\\' + parser.getArgs()[0];
        }
        // Full path
        else if(parser.getArgs()[0].charAt(0) == '\\'){
            filePath = parser.getArgs()[0];
        }
        File newFile = new File(filePath);
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
        }else{
        }
    }
    public void cat(){
        String[] args = parser.getArgs();
        if(args.length ==1){
            try{
                String firstFile=args[0];
                File file = new File(path+"\\"+firstFile);
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine())
                    System.out.println(sc.nextLine());
            }catch(IOException e){
                System.out.println("Error");
            }
        }else{
            try{
                String line,content="";
                String firstFile=args[0];
                String secondFile=args[1];
                FileReader frOne = new FileReader(path+"\\"+firstFile);
                BufferedReader brOne = new BufferedReader(frOne);
                FileReader frTwo = new FileReader(path+"\\"+secondFile);
                BufferedReader brTwo = new BufferedReader(frTwo);

                for(line=brOne.readLine(); line!=null; line=brOne.readLine())
                    content = content + line +" " ;
                brOne.close();

                for(line=brTwo.readLine(); line!=null; line=brTwo.readLine())
                    content = content + line;
                brTwo.close();
                System.out.println(content);


            }catch (IOException e){
                System.out.println("Error");
            }

        }
    }
    public void wc() {
        String name = parser.getArgs()[0];
        String file = name;
        if (path.charAt(path.length() - 1) != '\\') name = '\\' + name;
        int charNum = 0, wordNum = 0, lineNum = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path + name));
            String line;
            while ((line = reader.readLine()) != null) {
                String temp[] = line.split(" ");
                wordNum+=temp.length;
                charNum +=line.length();
                lineNum++;
            }
            System.out.println(lineNum + " " + wordNum + " " + charNum + " " + file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: No such file exists!");
        } catch (IOException e) {
            System.out.println("Error: Can't read from this file");
        }
    }
    public void greaterThanOperator(){
        if(!Objects.equals(parser.getArgs()[parser.getArgs().length - 2], ">")) {
            error = true;
            return;
        }
        String name = parser.getArgs()[parser.getArgs().length - 1];
        if (path.charAt(path.length() - 1) != '\\') name = '\\' + name;

        File file = new File(path+name);
        try {
            file.createNewFile();
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
        try {
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
        }
    }
    public void greaterThanOperator2(){
        if(!Objects.equals(parser.getArgs()[parser.getArgs().length - 2], ">>")) {
            error = true;
            return;
        }
        String name = parser.getArgs()[parser.getArgs().length - 1];
        if (path.charAt(path.length() - 1) != '\\') name = '\\' + name;
        String data = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path+name));
            String line;
            StringBuilder dataBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                dataBuilder.append(line).append('\n');
            }
            data = dataBuilder.toString();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        File file = new File(path+name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            error = true;
            return;
        }
        try {
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
            System.out.print(data);
        } catch (FileNotFoundException e) {
            error = true;
        }
    }

    public void chooseCommandAction(){

        String choice = parser.getCommandName();
        if(error) choice ="error";
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
        PrintStream os = System.out;
        while(true) {
            t.error = false;
            System.out.print(t.path + ">");
            String input = scan.nextLine();
            if (input.equals("exit"))
                break;
            t.parser.parse(input);
            for(int i = 0 ; i < t.parser.getArgs().length ; i++ ){
                String arg = t.parser.getArgs()[i];
                if(arg.equals(">")){
                    t.greaterThanOperator();
                    t.parser.removeLast2();
                }else if(arg.equals(">>")){
                    t.greaterThanOperator2();
                    t.parser.removeLast2();
                }
            }
            t.usedCommands.add(t.parser.getCommandName());
            t.chooseCommandAction();
            if(System.out != os) {
                System.out.close();
                System.setOut(os);
            }
        }
    }
}