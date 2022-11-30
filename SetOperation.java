import java.io.*;
import java.util.*;
//Class declarations
public class SetOperation {
    //subclass declaration
    class Movie{
        private int year;
        private String title;

        //Overriding the equals method
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Movie)) return false;
            Movie movie = (Movie) o;
            return year == movie.year && Objects.equals(title, movie.title);
        }
        //Overriding the hashcode method
        @Override
        public int hashCode() {
            return Objects.hash(year, title);
        }


        public Movie (String t, int y){
            this.title = t;
            this.year = y;
        }

        int getYear() {return year;}
        String getTitle() {return title;}

        @Override
        public String toString() {
            return year + " " + title;
        }
    }
    //Initializing sets
    Set<Movie> evenSet = new HashSet<Movie>();
    Set<Movie> oddSet = new HashSet<Movie>();
    //Constructor
    public SetOperation(){

        try {
            File x = new File("moviex.txt");
            File y = new File("moviey.txt");
            Scanner infilex = new Scanner(x);
            Scanner infiley = new Scanner(y);

            //Reading content of the first file and saving it in evenSet
            int temp_year;
            String temp_title;
            while (infilex.hasNextInt()){
                temp_year = infilex.nextInt();
                temp_title = infilex.nextLine();
                evenSet.add(new Movie(temp_title.replaceFirst(" ",""),temp_year));
            }
            //Reading content of the second file and saving it in oddSet
            int temp_year2;
            String temp_title2;
            while (infiley.hasNextInt()){
                temp_year2 = infiley.nextInt();
                temp_title2 = infiley.nextLine();
                oddSet.add(new Movie(temp_title2.replaceFirst(" ",""),temp_year2));
            }
            //Finding the intersection and printing it
            oddSet.retainAll(evenSet);
            Iterator<Movie> it = oddSet.iterator();
            while (it.hasNext()){
                Movie m = it.next();
                System.out.println(m);
            }

            infilex.close();
            infiley.close();
        }

        catch (FileNotFoundException fileException)
        {
            System.out.println("Files does not exist.");
            fileException.printStackTrace();
        }

    }


    //Main function
    public static void main(String[] args){
        SetOperation test = new SetOperation();
    }
}
