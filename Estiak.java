
// Imagine  a library system Where i need a track the number of books issued , 
//irrespective to the users;
class Library{
    private String username;
    private int userissued;
    private static int totalissued=0;
    public Library(String username){
        this.username=username;
        this.userissued=0;
    }
    public void givebook(){
        userissued++;
        totalissued++;
    }
    public void Showbook(){
        System.out.println("Book Taken by "+ username +"="+ userissued);
    }
}
public class Estiak{
    public static void main(String [] args){
    Library user1=new Library("Alice ");
    Library user2=new Library("Bob");
    user1.givebook();
    user1.givebook();
    
    user2.Showbook();
    user1.Showbook();
    }
}
