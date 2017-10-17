import java.io.*;
import java.util.*;

/**
 * Models hurricane information, works with Hurricane class
 * and the user to manipulate an array of hurricane data. 
 *
 * @author Helena Dworak
 * @author Susan King 
 * @version February 19, 2016
 */
public class HurricaneOrganizerArrayListHelena
{
    private ArrayList<Hurricane> hurricanes;

    /**
     * reads through file data of a hurricane
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     * @param   filename    the file to be read
     */
    public HurricaneOrganizerArrayListHelena(String filename)throws IOException
    {
        readFile(filename);   
    }

    /**
     * reads a file
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     * 
     * @param   filename    the file to be read
     */
    public void readFile(String filename) throws IOException
    {
        hurricanes = new ArrayList<Hurricane>();
        int hurYear, hurPressure, hurSpeed;
        String hurName, hurMonth;
        Scanner inFile = new Scanner(new File(filename));

        while (inFile.hasNextLine())
        {
            hurYear = inFile.nextInt();
            hurMonth = inFile.next();
            hurPressure = inFile.nextInt();
            hurSpeed = inFile.nextInt();
            String tempName = inFile.nextLine();
            hurName = "";
            for(int k = 0; k < tempName.length(); k++)
            {
                char c = tempName.charAt(k);
                if(('a' <= c && c <= 'z') || ('A' <= c && c <='Z'))
                    hurName += c;
            }
            Hurricane h = new Hurricane(hurYear, hurMonth, hurPressure, hurSpeed, hurName);
            hurricanes.add(h);
        }
        inFile.close();
    }

    /**
     * finds the maximum wind speed
     * 
     * @return the maximum wind speed
     */
    public int findMaxWindSpeed()
    {
        int max = 0;
        for (Hurricane h: hurricanes)
        {
            max = Math.max(max,h.getSpeed());
        }
        return max;
    }

    /**
     * finds the maximum pressure
     * 
     * @return the maximum pressure
     */
    public int findMaxPressure( )
    {
        int max = 0;
        for (Hurricane h: hurricanes)
        {
            max = Math.max(max,h.getPressure());
        }
        return max;
    }

    /**
     * finds the minimum wind speed
     * 
     * @return the minimum wind speed
     */
    public int findMinWindSpeed( )
    {
        int min = Integer.MAX_VALUE;
        for (Hurricane h: hurricanes)
        {
            min = Math.min(min, h.getSpeed());
        }
        return min;
    }

    /**
     * finds the minimum pressure
     * 
     * @return the maximum pressure
     */
    public int findMinPressure( )
    {
        int min = Integer.MAX_VALUE;
        for (Hurricane h: hurricanes)
        {
            min = Math.min(min, h.getPressure());
        }
        return min;
    }

    /**
     * Calculates average speed
     * @return average speed
     */
    public double calculateAverageWindSpeed( )
    {
        double sum = 0;
        for (Hurricane h : hurricanes)
        {
            sum += (double)h.getSpeed();
        }
        return sum/hurricanes.size();
    }

    /**
     * Returns average pressure 
     * @return average pressure
     */
    public double calculateAveragePressure( )
    {
        double sum = 0;
        for (Hurricane h : hurricanes)
        {
            sum += (double)h.getPressure();
        }
        return sum/hurricanes.size();
    }

    /**
     * Returns average category
     * 
     * @return the average category
     */
    public double calculateAverageCategory( )
    {
        double sum = 0;
        for (Hurricane h : hurricanes)
        {
            sum += (double)h.getCategory();
        }
        return sum/hurricanes.size();
    }

    /**
     * Sorts ascending based upon the hurricanes' years,
     * The algorithm is selection sort.
     */
    public void sortYears()
    {
        for (int outer = 0; outer < hurricanes.size(); outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < hurricanes.size(); inner++)
            {
                if(hurricanes.get(inner).compareYearTo(hurricanes.get(index)) < 1)
                    index = inner;
            }
            Hurricane h = hurricanes.get(index);
            hurricanes.set(index, hurricanes.get(outer));
            hurricanes.set(outer,h);
        }
    }

    /**
     * Lexicographically sorts hurricanes based on the hurricanes' name, 
     * using insertion sort.
     */
    public void sortNames()
    {
        for (int i = 0; i < hurricanes.size(); i++)
        {
            Hurricane h = hurricanes.get(i);
            int index = i - 1;
            while(index > -1 && h.compareNameTo(hurricanes.get(index)) < 1)
            {
                hurricanes.set(index + 1, hurricanes.get(index));
                index--;
            }
            hurricanes.set(index + 1, h);
        }
    }

    /**
     * Sorts descending based upon the hurricanes' categories,
     * using selection sort.
     */
    public void sortCategories()
    {
        for (int outer = 0; outer < hurricanes.size(); outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < hurricanes.size(); inner++)
            {
                if(hurricanes.get(inner).compareCategoryTo(hurricanes.get(index)) > 0)
                    index = inner;
            }
            Hurricane h = hurricanes.get(index);
            hurricanes.set(index, hurricanes.get(outer));
            hurricanes.set(outer,h);
        }
    }  

    /**
     * Sorts descending based upon pressures using a non-recursive merge sort.
     */
    public void sortPressures()
    {
        int len = hurricanes.size();
        int mid = len/2;
        sortPressuresHelper(0,mid);
        sortPressuresHelper(mid,len);
        int findex = 0;
        int sindex = mid;
        ArrayList<Hurricane> temp = new ArrayList<Hurricane>();
        for(int index = 0; index < len; index++)
        {
            if(findex >= mid)
            {
                temp.add(hurricanes.get(sindex));
                sindex++;
            }
            else if(sindex >= len)
            {
                temp.add(hurricanes.get(findex));
                findex++;
            }
            else if (hurricanes.get(findex).comparePressureTo(hurricanes.get(sindex)) > 0)
            {
                temp.add(hurricanes.get(findex));
                findex++;
            }
            else
            {
                temp.add(hurricanes.get(sindex));
                sindex++;
            }
        }
        hurricanes = temp;
    }
    
    /**
     * Sorts descending a portion of array based upon pressure, 
     * using selection sort.
     * 
     * @param   start   the first index to start the sort
     * @param   end     one past the last index to sort; hence, end position
     *                  is excluded in the sort
     */
    private void sortPressuresHelper (int start, int end)
    {
        for (int outer = 0; outer < hurricanes.size(); outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < hurricanes.size(); inner++)
            {
                if(hurricanes.get(inner).comparePressureTo(hurricanes.get(index)) > 0)
                    index = inner;
            }
            Hurricane h = hurricanes.get(index);
            hurricanes.set(index, hurricanes.get(outer));
            hurricanes.set(outer,h);
        }
    }

    /**
     * Sorts ascending based upon wind speeds using a recursive merge sort. 
     * 
     * @param   low     the base at which to start sorting
     * @param   high    the end at which to sort
     */
    public void sortWindSpeeds(int low, int high)
    {
        if(low == high)
            return;
        int mid = (low+high)/2;
        sortWindSpeeds(low, mid);
        sortWindSpeeds(mid+1, high);
        mergeWindSpeedsSortHelper(low, mid+1, high);
    }

    /**
     * Merges two consecutive parts of an array, using wind speed as a criteria
     * and a temporary array.  The merge results in an ascending sort between
     * the two given indices.
     * 
     * @precondition the two parts are sorted ascending based upon wind speed
     * 
     * @param low   the starting index of one part of the array.
     *              This index is included in the first half.
     * @param mid   the starting index of the second part of the array.
     *              This index is included in the second half.
     * @param high  the ending index of the second part of the array.  
     *              This index is included in the merge.
     */
    private void mergeWindSpeedsSortHelper(int low, int mid, int high)
    {
        int len = high - low +1;
        ArrayList<Hurricane> h = new ArrayList<Hurricane>();
        int findex = low;
        int sindex = mid;
        for(int index = 0; index < len; index++)
        {
            if(findex >= mid)
            {
                h.add(hurricanes.get(sindex));
                sindex++;
            }
            else if(sindex > high)
            {
                h.add(hurricanes.get(findex));
                findex++;
            }
            else if(hurricanes.get(sindex).compareSpeedTo(hurricanes.get(findex)) < 1)
            {
                h.add(hurricanes.get(sindex));
                sindex++;
            }
            else
            {
                h.add(hurricanes.get(findex));
                findex++;
            }
        }
        for (int i = 0; i < len; i++)
        {
            hurricanes.set(low + i, h.get(i));
        }
    }

    /**
     * Sequential search for all the hurricanes in a given year.
     * 
     * @param   year    the year to be searched
     * @return  an array of objects in Hurricane that occured in
     *          the parameter year
     */
    public ArrayList<Hurricane> searchYear(int year)
    {
        ArrayList<Hurricane> h = new ArrayList<Hurricane>();
        for (int i = 0; i < hurricanes.size(); i++)
        {
            if (hurricanes.get(i).getYear() == year)
            {
                h.add(hurricanes.get(i));
            }
        }
        return h;
    }     

    /**
     * Binary search for a hurricane name.
     * 
     * @param  name   hurricane name being search
     * @return a Hurricane array of all objects in hurricanes with specified name. 
     *         Returns null if there are no matches
     */
    public ArrayList<Hurricane> searchHurricaneName(String name)
    {
        sortNames();
        return searchHurricaneNameHelper(name, 0, hurricanes.size() - 1);
    }

    /**
     * Recursive binary search for a hurricane name.  This is the helper
     * for searchHurricaneName.
     * 
     * @precondition  the array must be presorted by the hurricane names
     * 
     * @param   name  hurricane name to search for
     * @param   low   the smallest index that needs to be checked
     * @param   high  the highest index that needs to be checked
     * @return  a Hurricane array of all Hurricane objects with a specified name. 
     *          Returns null if there are no matches
     */
    private ArrayList<Hurricane> searchHurricaneNameHelper(String name, int low , int high)
    {
        if (low > high) //base case
        {
            return null;
        }
        int mid = (low + high)/2;
        if(hurricanes.get(mid).getName().equals(name)) //base case
        {
            return retrieveMatchedNames(name, mid);
        }
        if(hurricanes.get(mid).getName().compareTo(name) > 0)
        {
            return searchHurricaneNameHelper(name, low, mid-1);
        }
        return searchHurricaneNameHelper(name, mid+1, high);
    }

    /**
     * Supports Binary Search method to get the full range of matches.
     * 
     * @precondition  the array must be presorted by the hurricane names
     * 
     * @param   name hurricane name being search for
     * @param   index  the index where a match was found
     * @return  a Hurricane array with objects from hurricanes with specified name. 
     *          Returns null if there are no matches
     */
    private ArrayList<Hurricane> retrieveMatchedNames (String name, int index)
    {
        int findex = index - 1;
        while (findex >= 0 && hurricanes.get(findex).getName().equals(name))
        {
            findex --;
        }
        findex ++;
        
        int lindex = index + 1;
        while (lindex < hurricanes.size() && hurricanes.get(lindex).getName().equals(name))
        {
            lindex++;
        }
        lindex--;
        ArrayList<Hurricane> matches = new ArrayList<Hurricane>();
        for (int i = 0; i < lindex-findex+1; i++)
        {
            matches.add(hurricanes.get(i+findex));
        }
        return matches;
    }

    /**
     * Prints the hurricane header so that data can be determined
     */
    public void printHeader()
    {
        System.out.println("\n\n");
        System.out.printf("%-4s %-5s %-15s %-5s %-5s %-5s \n", 
            "Year", "Mon.", "Name", "Cat.", "Knots", "Pressure");
    }

    /**
     * Prints out the default list of hurricanes and their data
     */
    public void printHurricanes()
    {
        printHurricanes(hurricanes);
    }

    /**
     * Prints out the parameter list of hurricanes and their data
     * 
     * @param   hurs    array of hurricanes to be printed
     */
    public void printHurricanes(ArrayList<Hurricane> hurs)
    {
        if(hurs == null || hurs.size() == 0)
        {
            System.out.println("\nVoid of hurricane data.");
            return;
        }
        printHeader();
        for(Hurricane h: hurs)
        {
            System.out.println(h);
        }
    }

    /**
     * Prints the menu for the user to choose from
     */
    public void printMenu()
    {
        System.out.println("\n\nEnter option: ");
        System.out.println("\t 1 - Print all hurricane data \n" +
            "\t 2 - Print maximum and minimum data \n" +
            "\t 3 - Print averages \n" +
            "\t 4 - Sort hurricanes by year \n" +
            "\t 5 - Sort hurricanes by name \n" +
            "\t 6 - Sort hurricanes by category, descending \n" +
            "\t 7 - Sort hurricanes by pressure, descending \n" +
            "\t 8 - Sort hurricanes by speed \n" + 
            "\t 9 - Search for hurricanes for a given year \n" +
            "\t10 - Search for a given hurricane by name \n" +
            "\t11 - Quit \n");
    }

    /**
     * Prints the max and min of the hurricane data. 
     */
    public void printMaxAndMin( )
    {
        System.out.println("Maximum wind speed is " + 
            findMaxWindSpeed( ) +
            " knots and minimum wind speed is " + 
            findMinWindSpeed( ) + " knots.");
        System.out.println("Maximum pressure is " + 
            findMaxPressure( ) +
            " and minimum pressure is " + 
            findMinPressure( ) + ".");
    }

    /**
     * Prints the averages of all of the hurricane data
     */
    public void printAverages( )
    {
        System.out.printf("Average wind speed is %5.2f knots. \n" , 
            calculateAverageWindSpeed( ));
        System.out.printf("Average pressure is %5.2f. \n" , 
            calculateAveragePressure( ));
        System.out.printf("Average category is %5.2f. \n" , 
            calculateAverageCategory( ));
    }

    /**
     * Receives data from user and determines which method to run
     * 
     * @return done when the user quits
     */
    public boolean interactWithUser( )
    {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        printMenu();
        int choice = in.nextInt();
        // clear the input buffer
        in.nextLine();

        if(choice == 1)
        {
            printHurricanes( ); 
        }
        else if (choice == 2)
        {
            printMaxAndMin( );
        }
        else if (choice == 3)
        {
            printAverages( );
        }
        else if(choice == 4)
        {
            sortYears();
            printHurricanes( );
        }
        else if(choice == 5)
        {
            sortNames();
            printHurricanes( );
        }
        else if(choice == 6)
        {
            sortCategories();
            printHurricanes( );
        }
        else if(choice == 7)
        {
            sortPressures();
            printHurricanes( );
        }
        else if(choice == 8)
        {
            sortWindSpeeds(0, hurricanes.size() - 1);
            printHurricanes( );
        }
        else if(choice == 9)
        {
            System.out.print("\n\tWhich year do you want to search for?\n\t");
            int year = in.nextInt();
            printHurricanes(searchYear(year));
        }
        else if(choice == 10)
        {
            System.out.print("\n\tWhich name do you want to search for?\n\t");
            String name = in.next();
            printHurricanes(searchHurricaneName(name));
        }
        else if (choice == 11)
        {
            done = true;
        }  
        return done;
    }

    /**
     * Runs the organizer allowing user to choose how to sort data
     * 
     * @param args  user's information from the command line
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     */
    public static void main (String [] args) throws IOException
    {
        HurricaneOrganizerArrayListHelena cane = 
            new HurricaneOrganizerArrayListHelena("hurricanedata.txt");
        boolean areWeDoneYet = false;
        while ( ! areWeDoneYet)
        {
            areWeDoneYet = cane.interactWithUser( );    
        }
    }
}
