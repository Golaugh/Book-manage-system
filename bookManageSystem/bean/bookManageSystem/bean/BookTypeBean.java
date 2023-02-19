package bookManageSystem.bean;

public class BookTypeBean {
    private  String 借阅日期;
    private  String 还书日期;
    private String bookTypeId;
    private String author;
    private String bookTypepubilsh;
    private int shuliang;
    private String book复合名称;
    private String xuehao;
    private String xingming;
    private String banji;
    private String yuliang;
    private String imfo书名;
    private String imfo姓名;

    public BookTypeBean() {
    }

    public BookTypeBean(String bookTypeId, String bookTypeName, String bookTypepubilsh) {
        this.bookTypeId = bookTypeId;
        this.author = bookTypeName;
        this.bookTypepubilsh = bookTypepubilsh;
    }

    public String getBook书名() {
        return bookTypeId;
    }

    public void setBookname(String bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookauthor() {
        return author;
    }

    public void setBookauthor(String bookTypeauthor) {
        this.author = bookTypeauthor;
    }

    public String getBookTypepubilsh() {
        return bookTypepubilsh;
    }

    public void setBookpublish(String bookTypepublish) {
        this.bookTypepubilsh = bookTypepublish;
    }

    public int getBookshuliang(){
        return shuliang;
    }

    public void setbookshuliang(int shuliang) {
        this.shuliang=shuliang;
    }

    public void setBook复合名称(String 复合名称) {
        this.book复合名称=复合名称;
    }
    public String getBook复合名称(){
        return book复合名称;
    }

    public void set学号(String xuehao) {
        this.xuehao=xuehao;
    }
    public String get学号() {
        return xuehao;
    }



    public void set姓名(String xingming) {
        this.xingming=xingming;
    }
    public String get姓名() {
        return xingming;
    }



    public void set班级(String banji) {
        this.banji=banji;
    }
    public String get班级() {
        return banji;
    }


    public void set可借书数目(String yuliang) {
        this.yuliang=yuliang;
    }
    public String get剩余可借书目() {
        return yuliang;
    }


    public void setimfo书名(String imfo书名) {
        this.imfo书名=imfo书名;
    }
    public String getimfo书名() {
        return  imfo书名;
    }



    public void set借阅日期(String 借阅日期) {
        this.借阅日期=借阅日期;
    }
    public String get借阅日期() {
        return 借阅日期;
    }



    public void set还书日期(String 还书日期) {
        this.还书日期=还书日期;
    }
    public String get还书日期() {
        return 还书日期;
    }



    public void setimfo姓名(String imfo姓名) {
        this.imfo姓名=imfo姓名;
    }
    public String getimfo姓名() {
        return imfo姓名;
    }
}
