package db;

import util.BookTM;
import util.IBookCartTM;
import util.MemberTM;

import java.util.ArrayList;

public class DB {

    public static ArrayList<MemberTM> memberlist = new ArrayList<>();
    public static ArrayList<BookTM> booklist = new ArrayList<>();
    public static ArrayList<IBookCartTM> tblCart = new ArrayList<>();
    public static ArrayList<IssueDetail> issuedBookList = new ArrayList<>();

    static {

        memberlist.add(new MemberTM("M001", "Nipun", "Galle"));
        memberlist.add(new MemberTM("M002", "Heshan", "Kandy"));
        memberlist.add(new MemberTM("M003", "Leel", "Matara"));

        booklist.add(new BookTM("B001","Atomic Habits","Nipun",1500,"Available"));
        booklist.add(new BookTM("B002","SignalMan","Henry",800,"Available"));
        booklist.add(new BookTM("B003","The Devil","Devin",900,"Available"));






    }


}
