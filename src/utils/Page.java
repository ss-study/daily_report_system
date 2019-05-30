package utils;

public class Page {

    private Integer page;

    public Page setPage(String page){
        try{
            this.page = Integer.parseInt(page);
        }catch(Exception e){
            this.page = 1;
        }
        return this;
    }

    public Integer toInteger() {
        return this.page;
    }

}
