import java.util.*;

//*******************************************************************
// Class Name: Simulator
//
// Description: This class is the simulator of search engine.
// It collects the most fundamental functions this search engine
//*******************************************************************
public class Simulator {

    private ArrayList<URL> urlList;
    private ArrayList<URL> saveScoreChangedList = new ArrayList<URL>();
    private HashMap<String, Integer> keywordMap = new HashMap<String, Integer>();
    private Scanner input = new Scanner(System.in);

//*******************************************************************
//  Method Name: Search
//
// Description: This method collects all the partial functions to make a search
// it initialize the arraylist that store the result links get the keyword from user
// save the keyword for later use in print top 10 keywords
// then use web crawler to get results
// add the results into an arraylist for easy sort
// Then sort the arraylist and print the top 10
//*******************************************************************
    public void search() {
        urlList = new ArrayList<URL>();
        System.out.println("Please enter your keyword: ");
        String keyword = input.next().toLowerCase();
        storeKeyword(keyword);//save the keyword to the hashmap
        WebCrawler c = new WebCrawler(keyword);
        c.search();//do the search

        //put links into an Arraylist then sort it
        for (String url : c.getUrls()) {
            if (urlList.size() < 30) {//only put 30 links to the arraylist
                URL aUrl;
                if (checkUrlScoreChanged(url)) {//check if the URL's score is manually changed before
                    aUrl = saveScoreChangedList.get(getSavedUrlIndex(url));
                } else {
                    aUrl = new URL(url);
                }
                urlList.add(aUrl);
                aUrl.setIndex(urlList.size());//set the original index is 1-30
            }
        }

        ArrayList<URL> copyOfList = new ArrayList<URL>(urlList);//make a copy so that can keep the original list after extract
        getTop10Result(urlList);
        urlList = copyOfList;
    }

    //*******************************************************************
//  Method Name:changeScore
//
// Description: Allow user to input a URL to change the score of a website.
// It first will show the current 30 urls with the score in the list to the users
// get the URL from the user and check if the URL is a new one or already in the result
// Subroutine1: if the URL already exists, print the current score
// ask user to change the score they want to change to
// then change the score
// Subroutine2: if the URL is new, create a new URL object
// ask user to set the score, insert it onto the heap tree
//*******************************************************************
    public void changeScore() {
        if(urlList == null){
            System.out.println("There is no search result, please do a search before you change the score. ");
        }else{
            HeapSort urlSort = new HeapSort(urlList);
            System.out.println("This is all the URLs in the result list");
            urlSort.HeapSort(urlList);
            urlSort.printList(urlList);
            System.out.println("Please enter the link of the website that you want to change the score: ");
            String url = input.next();
            int urlIndex = 0;//for easy access the element
            boolean contain = false;
            for (URL temp : urlList) {
                if (temp.getUrl().equals(url)) {
                    contain = true;
                    urlIndex = urlList.indexOf(temp);
                    break;
                }
            }
            if (contain) {//if the link already in the urlList
                //display current score
                System.out.println("Current Score: ");
                print4Scores(urlList.get(urlIndex));
                setNewScores(urlList.get(urlIndex));
                saveScoreChangedList.add(urlList.get(urlIndex));//save the URL
                urlSort.setHeapSize(urlList);
                urlSort.BuildMaxHeap(urlList);//rebuild the tree
            } else {//the link is new to the urlList
                URL aNewUrl = new URL(url);
                System.out.println("Current Score: ");
                print4Scores(urlList.get(urlIndex));
                setNewScores(aNewUrl);
                saveScoreChangedList.add(aNewUrl);
                urlSort.setHeapSize(urlList);
                urlSort.BuildMaxHeap(urlList);
                urlSort.MaxHeapInsert(urlList, aNewUrl);
                aNewUrl.setIndex(urlList.size());
            }

//this three must be used as a set
//        ArrayList<URL> copyOfList = new ArrayList<URL>(urlList);
//        getTop10Result(urlList);
//        urlList = copyOfList;
        }

    }

    //*******************************************************************
// Method Name: print4Scores
//
// Description: Print out the current 4 factors scores of a URL
//*******************************************************************
    public void print4Scores(URL aUrl) {
        System.out.println("KeywordScore: " + aUrl.getKeywordsScore()
                + "\nExisted Score: " + aUrl.getExistedStore()
                + "\nOwnerPaid Score: " + aUrl.getOwnerPaidScore()
                + "\nPageLinkTo Score: " + aUrl.getPageLinkToScore()
                + "\nTotal Score: " + aUrl.getTotalScore());
    }

    //*******************************************************************
// Method Name: setNewScores
//
// Description: Ask user to set the new 4 factors scores
//*******************************************************************
    public void setNewScores(URL aUrl) {
        System.out.println("Please enter the new keyword score");
        int newKeywordScore = input.nextInt();
        aUrl.setKeywordsScore(newKeywordScore);
        System.out.println("Please enter the new existed score");
        int newExistedScore = input.nextInt();
        aUrl.setExistedStore(newExistedScore);
        System.out.println("Please enter the new page ink to score");
        int newPageLinkToScore = input.nextInt();
        aUrl.setPageLinkToScore(newPageLinkToScore);
        System.out.println("Please enter the new owner paid score");
        int newOwnerPaidScore = input.nextInt();
        aUrl.setOwnerPaidScore(newOwnerPaidScore);
    }

    //*******************************************************************
//  Method Name: storeKeyword
//
// Description: Save the Keyword that user entered
// check if the keywords already exist in the hash map
// if not exist, put the new key into the map and give value 1
// if exist, update the key's value
//*******************************************************************
    public void storeKeyword(String aKeyword) {
        if (keywordMap.containsKey(aKeyword)) {
            int oldValue = keywordMap.get(aKeyword);
            keywordMap.put(aKeyword, keywordMap.get(aKeyword) + 1);
        } else {
            keywordMap.put(aKeyword, 1);
        }
    }

    //*******************************************************************
//  Method Name: getTop10Result
//
// Description: The method will build the heap tree of the urls by scores
// extract the top 10 urls and print them out
//*******************************************************************
    public void getTop10Result(ArrayList<URL> urlList) {
        HeapSort urlSort = new HeapSort(urlList);
        urlSort.setHeapSize(urlList);
        urlSort.BuildMaxHeap(urlList);
        for (int i = 0; i < 10; i++) {
            URL aUrl = urlSort.HeapExtractMax(urlList);
            System.out.println("Index: "+aUrl.getIndex()+" Total score: "+aUrl.getTotalScore() + " Page Rank:" +(i+1)+" URL: " + aUrl.getUrl());
        }//make sure only top "10" print out


    }

    //*******************************************************************
//  Method Name: printTop10Keyword
//
// Description: The method will sort the hashMap by value and print the Top 10 keywords
//*******************************************************************
    public void printTop10Keyword() {
        List<Map.Entry<String, Integer>> temp = new LinkedList<Map.Entry<String, Integer>>(keywordMap.entrySet());//put the map into a list for sorting
        Collections.sort(temp, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> key1, Map.Entry<String, Integer> key2) {
                if (key1.getValue() > key2.getValue()) {
                    return -1;//not 1
                } else if (key1.getValue() < key2.getValue()) {
                    return 1;//not -1
                }
                return 0;
            }
        });

        int i = 0;//a counter for get top 10
        for (Map.Entry<String, Integer> tempKey : temp) {
            System.out.println("Key: " + tempKey.getKey() + " Value: " + tempKey.getValue());
            i++;
            if (i >= 10) {
                break;
            }//when i = 10, it already print 10 times so stop
        }
    }

    //*******************************************************************
//  Method Name: checkUrlScoreChanged
//
// Description: The method will check if the user already changed the score of a website in the past search
//*******************************************************************
    public boolean checkUrlScoreChanged(String aUrl) {
        for (URL temp : saveScoreChangedList) {
            if (aUrl.equals(temp.getUrl())) {//to find the equal String url
                return true;
            }
        }
        return false;
    }

    //*******************************************************************
//  Method Name: getSavedUrlIndex
//
// Description: The method will get the saved URL with its changed score
// It will called after checkUrlScoreChanged
//*******************************************************************
    public int getSavedUrlIndex(String url) {
        for (URL temp : saveScoreChangedList) {
            if (temp.getUrl().equals(url)) {//to find the equal String url
                return saveScoreChangedList.indexOf(temp);
            }
        }
        return 0;
    }
}
