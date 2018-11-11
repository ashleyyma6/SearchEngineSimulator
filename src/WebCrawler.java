import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//*******************************************************************
// Class Name: WebCrawler
//
// Description: By using this class to create a web crawler which can crawl URLs
// from the Internet.
//*******************************************************************
public class WebCrawler {

    private String url;
    private String keyword;
    private Set<String> urls = new HashSet<String>();

    private static final String USER_AGENT =
            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    private Document htmlDocument;
    private static Pattern patternDomainName;
    private Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN
            = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
    //*******************************************************************
// No method name
//
// Description: This is a constructor to set a format.
// It is used to extract the real domain link from a google search result link
//*******************************************************************
    static {
        patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }

    //*******************************************************************
// Method Name: WebCrawler
//
// Description: Constructor for the WebCrawler object
// It saved the keyword that user entered and put it into a google search link
//*******************************************************************
    WebCrawler(String aKeyword) {
        keyword = aKeyword;
        url = "https://google.com/search?q=" + aKeyword + "&num=80";
    }

    //*******************************************************************
// Method Name: Search
//
// Description: This method start the search
// It will check if the page contains the keyword, then call crawl method to crawl the URLs on the page
//*******************************************************************
    public void search() {
        String currentUrl = url;
        crawl(currentUrl);
        boolean success = searchForWord(keyword);
        if (success) {
            System.out.println(String.format("**Success** Word %s found at %s", keyword, currentUrl));
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", urls.size()));

    }

    //*******************************************************************
//  Method Name: crawl
//
// Description: The method will crawl the links and put them in to a set to keep uniqueness
//*******************************************************************
    //Give it a URL and it makes an HTTP request for a web page
    public boolean crawl(String url) {
        try {

            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            final Document htmlDocument = connection.timeout(5000).get();
            this.htmlDocument = htmlDocument;

            if (connection.response().statusCode() == 200) {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            for (Element link : linksOnPage) {
                String temp = link.attr("href");
                if (temp.startsWith("/url?q=http")) {
                    this.urls.add(getDomainName(temp));
                }
            }
            //printUrlSet();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    //*******************************************************************
//  Method Name: getDomainName
//
// Description: The method will use pattern and matcher to extract the domain url from google result links
//*******************************************************************
    public String getDomainName(String url) {
        matcher = patternDomainName.matcher(url);
        if (url.startsWith("/url?q=https://")) {
            return url.substring(15, 42);
        }
        return url.substring(14,42);
    }

    //*******************************************************************
//  Method Name: searchForWord
//
// Description: This method will check if the website contains keyword
//*******************************************************************
    // Tries to find a word on the page
    public boolean searchForWord(String searchWord) {
        if (this.htmlDocument == null) {
            System.out.println("Error!");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }

    //*******************************************************************
//  Method Name: getUrls
//
// Description: Accessor to get the set of urls result
//*******************************************************************
    // Returns a list of all the URLs on the page
    public Set<String> getUrls() {
        return this.urls;
    }

}
