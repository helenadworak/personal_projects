import java.io.*;
import java.util.*;

/**
 * Models hurricane information, works with Hurricane class
 * and the user to manipulate an array of hurricane data. 
 *
 * @author Susan King 
 * @author Helena Dworak
 * @version December 28, 2015
 */
public class HurricaneOrganizerArrayHelena
{
    private Hurricane [] hurricanes;

    /**
     * reads through file data of a hurricane
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     * @param   filename    the name of the file to be read
     */
    public HurricaneOrganizerArrayHelena(String filename)throws IOException
    {
        readFile(filename);   
    }

    /**
     * determines the length of the file with hurricane data
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     */
    private static int determineFileLength(String filename) throws IOException
    {
        Scanner inFile = new Scanner(new File(filename));
        int counter = 0;
        while(inFile.hasNextLine())
        {
            counter++;
            inFile.nextLine();
        }
        inFile.close();
        return counter;
    }

    /**
     * reads a file
     * 
     * @throws IOException  if file with the hurricane information cannot be found
     * @param   filename    the name of the file to be read
     */
    public void readFile(String filename) throws IOException
    {
        hurricanes = new Hurricane [determineFileLength(filename)];
        int hurYear, hurPressure, hurSpeed;
        String hurName, hurMonth;
        Scanner inFile = new Scanner(new File(filename));

        for(int i = 0; i < hurricanes.length; i++)
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
            hurricanes [i] = h;
        }
        inFile.close();
    }

    /**
     * finds the maximum wind speed
     * 
     * @return  max wind speed
     * 
     */
    public int findMaxWindSpeed()
    {
        int max = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            max = Math.max(max,hurricanes[i].getSpeed());
        }
        return max;
    }

    /**
     * finds the maximum pressure
     * 
     * @return maximum pressure
     */
    public int findMaxPressure( )
    {
        int max = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            max = Math.max(max,hurricanes[i].getPressure());
        }
        return max;
    }

    /**
     * finds the minimum wind speed
     * 
     * @return minimum wind speed
     */
    public int findMinWindSpeed( )
    {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < hurricanes.length; i++)
        {
            min = Math.min(min, hurricanes[i].getSpeed());
        }
        return min;
    }

    /**
     * Finds the minimum pressure.
     * 
     * @return minimum pressure
     */
    public int findMinPressure( )
    {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < hurricanes.length; i++)
        {
            min = Math.min(min, hurricanes[i].getPressure());
        }
        return min;
    }

    /**
     * Calculates the average wind speed.
     * 
     * @return the average of all summed speeds
     */
    public double calculateAverageWindSpeed( )
    {
        double sum = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            sum += (double)hurricanes[i].getSpeed();
        }
        return sum/hurricanes.length;
    }

    /**
     * Calculates the average pressure.
     * 
     * @return the average of all summed pressures
     */
    public double calculateAveragePressure( )
    {
        double sum = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            sum += (double)hurricanes[i].getPressure();
        }
        return sum/hurricanes.length;
    }

    /**
     * Calculates the average category.
     * 
     * @return the average of all summed categories
     */
    public double calculateAverageCategory( )
    {
        double sum = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            sum += (double)hurricanes[i].getCategory();
        }
        return sum/hurricanes.length;
    }

    /**
     * Sorts ascending based upon the hurricanes' years,
     * The algorithm is selection sort.
     */
    public void sortYears()
    {
        for (int outer = 0; outer < hurricanes.length; outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < hurricanes.length; inner ++)
            {
                if(hurricanes[inner].compareYearTo(hurricanes[index]) < 1)
                    index = inner;
            }
            Hurricane h = hurricanes[index];
            hurricanes[index] = hurricanes[outer];
            hurricanes[outer] = h;
        }
    }

    /**
     * Lexicographically sorts hurricanes based on the hurricanes' name, 
     * using insertion sort.
     */
    public void sortNames()
    {
        for (int i = 0; i < hurricanes.length; i++)
        {
            Hurricane h = hurricanes[i];
            int index = i - 1;
            while(index > -1 && h.compareNameTo(hurricanes[index]) < 1)
            {
                hurricanes[index + 1] = hurricanes[index];
                index--;
            }
            hurricanes[index + 1] = h;
        }
    }

    /**
     * Sorts descending based upon the hurricanes' categories,
     * using selection sort.
     */
    public void sortCategories()
    {
        for (int outer = 0; outer < hurricanes.length; outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < hurricanes.length; inner ++)
            {
                if(hurricanes[inner].compareCategoryTo(hurricanes[index]) > 0)
                    index = inner;
            }
            Hurricane h = hurricanes[index];
            hurricanes[index] = hurricanes[outer];
            hurricanes[outer] = h;
        }
    }  

    /**
     * Sorts descending based upon pressures using a non-recursive merge sort.
     */
    public void sortPressures()
    {
        int len = hurricanes.length;
        int mid = len/2;
        sortPressuresHelper(0,mid);
        sortPressuresHelper(mid,len);
        int findex = 0;
        int sindex = mid;
        Hurricane[] temp = new Hurricane[len];
        for(int index = 0; index < len; index++)
        {
            if(findex >= mid)
            {
                temp[index] = hurricanes[sindex];
                sindex++;
            }
            else if(sindex >= len)
            {
                temp[index] = hurricanes[findex];
                findex++;
            }
            else if (hurricanes[findex].comparePressureTo(hurricanes[sindex]) > 0)
            {
                temp[index] = hurricanes[findex];
                findex++;
            }
            else
            {
                temp[index] = hurricanes[sindex];
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
        for (int outer = start; outer < end; outer++)
        {
            int index = outer;
            for (int inner = outer+1; inner < end; inner ++)
            {
                if(hurricanes[inner].comparePressureTo(hurricanes[index]) > 0)
                    index = inner;
            }
            Hurricane h = hurricanes[index];
            hurricanes[index] = hurricanes[outer];
            hurricanes[outer] = h;
        }
    }

    /**
     * Sorts ascending based upon wind speeds using a recursive merge sort. 
     * 
     * @param   low     the minimum value at which to start sorting 
     * 
     * @param   high    the maximum value at which to start sorting 
     */
    public void sortWindSpeeds(int low, int high)
    {
        if(low == high) //base case
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
        Hurricane[] h = new Hurricane[len];
        int findex = low;
        int sindex = mid;
        for(int index = 0; index < len; index++)
        {
            if(findex >= mid)
            {
                h[index] = hurricanes[sindex];
                sindex++;
            }
            else if(sindex > high)
            {
                h[index] = hurricanes[findex];
                findex++;
            }
            else if(hurricanes[sindex].compareSpeedTo(hurricanes[findex]) < 1)
            {
                h[index] = hurricanes[sindex];
                sindex++;
            }
            else
            {
                h[index] = hurricanes[findex];
                findex++;
            }
        }
        for (int i = 0; i < len; i++)
        {
            hurricanes[low + i] = h[i];
        }
    }

    /**
     * Sequential search for all the hurricanes in a given year.
     * 
     * @param   year    year to be searched
     * @return  an array of objects in Hurricane that occured in
     *          the parameter year
     */
    public Hurricane [] searchYear(int year)
    {
        int counter = 0;
        for ( int i = 0 ; i < hurricanes.length; i++)
        {
            if(hurricanes[i].getYear() == year)
                counter ++;
        }
        Hurricane [] h = new Hurricane[counter];
        int index = 0;
        for (int i = 0; i < hurricanes.length; i++)
        {
            if (hurricanes[i].getYear() == year)
            {
                h[index] = hurricanes[i];
                index++;
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
    public Hurricane[ ] searchHurricaneName(String name)
    {
        sortNames();
        return searchHurricaneNameHelper(name, 0, hurricanes.length - 1);
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
    private Hurricane[ ] searchHurricaneNameHelper(String name, int low , int high)
    {
        if (low > high) //base case
        {
            return null;
        }
        int mid = (low + high)/2;
        if(hurricanes[mid].getName().equals(name)) //base case
        {
            return retrieveMatchedNames(name, mid);
        }
        if(hurricanes[mid].getName().compareTo(name) > 0)
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
    private Hurricane[ ] retrieveMatchedNames (String name, int index)
    {
        int findex = index - 1;
        while (findex >= 0 && hurricanes[findex].getName().equals(name))
        {
            findex --;
        }
        findex ++;
        
        int lindex = index + 1;
        while (lindex < hurricanes.length && hurricanes[lindex].getName().equals(name))
        {
            lindex++;
        }
        lindex--;

        Hurricane [] matches = new Hurricane[lindex-findex+1];
        for (int i = 0; i < matches.length; i++)
        {
            matches[i] = hurricanes[i+findex];
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
    public void printHurricanes(Hurricane [] hurs)
    {
        if( hurs == null || hurs.length == 0)
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
     * @return done when user quits
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
            sortWindSpeeds(0, hurricanes.length - 1);
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
        HurricaneOrganizerArrayHelena cane = new HurricaneOrganizerArrayHelena("hurricanedata.txt");
        boolean areWeDoneYet = false;
        while ( ! areWeDoneYet)
        {
            areWeDoneYet = cane.interactWithUser( );    
        }
    }
}
