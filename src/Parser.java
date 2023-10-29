public class Parser {
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