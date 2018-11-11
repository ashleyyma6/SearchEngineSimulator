import java.lang.Math;

/**
 * To store a URL and its scores in a object
 */
//*******************************************************************
// Class Name: URL
//
// Description: This class is for storing the URL and its scores
//*******************************************************************

public class URL {

    private String url;
    private int keywordsScore;
    private int existedStore;
    private int pageLinkToScore;
    private int ownerPaidScore;
    private int totalScore;
    private int index;

    //*******************************************************************
//  Method Name: Constructor
//
// Description: To save the url, usually the owner paid 0 at first
//*******************************************************************
    URL(String aURL) {
        url = aURL;
        ownerPaidScore = 0;
        keywordsScore = (int) (Math.random() * 26);
        existedStore = (int) (Math.random() * 26);
        pageLinkToScore = (int) (Math.random() * 26);
    }

    //*******************************************************************
//  Method Name: setOwnerPaidScore
//
// Description: To set a new score to the OwnerPaidScore
//*******************************************************************
    void setOwnerPaidScore(int aOwnerPaidScore) {
        ownerPaidScore = aOwnerPaidScore;
    }

    //*******************************************************************
//  Method Name: setKeywordsScore
//
// Description: To set a new score to the KeywordsScore
//*******************************************************************
    void setKeywordsScore(int aKeywordScore) {
        keywordsScore = aKeywordScore;
    }

    //*******************************************************************
//  Method Name: setExistedStore
//
// Description: To set a new score to the ExistedStore
//*******************************************************************
    void setExistedStore(int aExistedStore) {
        existedStore = aExistedStore;
    }

    //*******************************************************************
//  Method Name: setPageLinkToScore
//
// Description: To set a new score to the PageLinkToScore
//*******************************************************************
    void setPageLinkToScore(int aPageLinkToScore) {
        pageLinkToScore = aPageLinkToScore;
    }

    //*******************************************************************
//  Method Name: setTotalScore
//
// Description: To set the TotalScore to a specific number
//// used in the priority queue to set it to be infinity
//*******************************************************************
    //
    void setTotalScore(int aTotalScore) {
        totalScore = aTotalScore;
    }
    //*******************************************************************
//  Method Name: setIndex
//
// Description: To set(save) the page index(the index before it is sorted)
//*******************************************************************
    void setIndex(int aIndex){
        index = aIndex;
    }

    //*******************************************************************
//  Method Name: getTotalScore
//
// Description: Accessor to get the TotalScore
//*******************************************************************
    int getTotalScore() {
        totalScore = keywordsScore + existedStore + pageLinkToScore + ownerPaidScore;
        return totalScore;
    }

    //*******************************************************************
//  Method Name: getOwnerPaidScore
//
// Description: Accessor to get the OwnerPaidScore
//*******************************************************************
    int getOwnerPaidScore() {
        return ownerPaidScore;
    }

    //*******************************************************************
//  Method Name: getKeywordsScore
//
// Description: Accessor to get the KeywordsScore
//*******************************************************************
    int getKeywordsScore() {
        return keywordsScore;
    }

    //*******************************************************************
//  Method Name: getExistedStore
//
// Description: Accessor to get the ExistedStore
//*******************************************************************
    int getExistedStore() {
        return existedStore;
    }

    //*******************************************************************
//  Method Name: getPageLinkToScore
//
// Description: Accessor to get the PageLinkToScore
//*******************************************************************
    int getPageLinkToScore() {
        return pageLinkToScore;
    }

    //*******************************************************************
//  Method Name: getUrl
//
// Description: Accessor to get the URL
//***************************************************** **************
    String getUrl() {
        return url;
    }
    //*******************************************************************
//  Method Name: getIndex
//
// Description: Accessor to get the index(the index before it is sorted)
//*******************************************************************
    int getIndex(){
        return index;
    }

}
