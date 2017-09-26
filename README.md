# PersonalCapitalChallenge
Improvements I would make
* Adding a share button to the article card for people to be instantly able to share it on different platforms
* Calculating the views on article (No of times url was visited) and showing that as a part of the article card.
* The XML in the rss feed returns categories relevant to the article, I would display top few tags below the article and provide a way to filter feed based on those tags , ability to subscribe to categories and notifications when new articles pop up in that category.
* Pull to refresh
* Lazy loading on scroll.
* Background service to push new articles to app.
* "Recommended for you" section.
Code improvements :-
* DB optimization :- I am storing the articles locally for offline support for people to come to a cached copy of article metadata rather than a blank screen. However, I currently delete and reinsert into the db after every network fetch, instead I would store them based on a key and simply update articles that were already fetched.

Improvements I would make
* Adding a share button to the article card for people to be instantly able to share it on different platforms
* Calculating the views on article (No of times url was visited) and showing that as a part of the article card.
* The XML in the rss feed returns categories relevant to the article, I would display top few tags below the article and provide a way to filter feed based on those tags , ability to subscribe to categories and notifications when new articles pop up in that category.
* Pull to refresh
* Lazy loading on scroll.
* Background service to push new articles to app.
* "Recommended for you" section.
Code improvements :-
* DB optimization :- I am storing the articles locally for offline support for people to come to a cached copy of article metadata rather than a blank screen. However, I currently delete and reinsert into the db after every network fetch, instead I would store them based on a key and simply update articles that were already fetched.
Requirements :- Create a mobile application that can be displayed in all orientations. The application will do the following: ● Parse the RSS feed https://blog.personalcapital.com/feed/?cat=3,891,890,68,284 and asynchronously load the contents of the feed. Display a loading indicator while the feed is loading as well as for each image. ○ Each article is represented in an item node. Each article has an html encoded title represented in the title node, an image represented in the media:content node, a quick html encoded summary represented in the description node, a publish date represented in the pubDate node, and a link to the article actual article represented in the link node.
● The main screen should display the title of the feed represented in the feed’s html encoded title node and display each article in a scrolling list that correctly utilizes the space of the device screen. ○ The first article should take prominence at the top and display the image, title on one line with the rest ellipsed if necessary, and the first two lines of the summary with the rest ellipsed if necessary. ○ Each article after should be displayed underneath the main article and represented by the image and title underneath (rendering at most 2 lines of the title with the rest ellipsed if necessary). ○ For handset, the articles should be in rows of 2. ○ For tablet, the articles should be in rows of 3. ○ HTML encoded content should be rendered correctly. ○ The screen should contain a “refresh” button in the upper right corner of the navigation bar which will query the RSS feed and refresh the screen. ● Selecting an article will render the article’s link in an embedded webview on another screen with the title of the article displayed in the navigation bar. ○ “?displayMobileNavigation=0” needs to be appended to each article’s link.
