public class Parser {
    private String commandName = "";
    private String[] args;

    public void parse(String input){
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
}