public class WordCounter {

    public String processText(StringBuffer text, String stopword) {
        if (stopword == null) {
            stopwordnull(text);
        }else{
            stopwordtext(text, stopword);
        }

    }

    public int stopwordtext(StringBuffer text, String stopword) throws InvalidStopwordException{
        try{
            int count = 0;
            for (int i = 0; i < text.length(); i++){
                count = count + 1;
                int stopwordlength = stopword.length();
                if (text.charAt(i) == stopword.charAt(i)){
                    if (i + stopword.length() == count){
                        for (int k = stopwordlength; k > 0; k--){
                        }
                        break;
                    }else{
                        continue;
                    }
                    
                }else{
                    throw InvalidStopwordException e;
                }
            }
        }
        catch (InvalidStopwordException e) {

        }
    }

    public int stopwordnull(StringBuffer text) throws TooSmallText{
        try{
            int count = 0;
            for (int i = 0; i < text.length(); i++){
                count++;
            }
            if (count < 5){
                throw TooSmallText e;
            }
            return count;
        }catch (TooSmallText e){
            break;
        }
    }
}