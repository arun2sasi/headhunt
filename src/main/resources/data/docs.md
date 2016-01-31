# Documentation

Here is documentation for application.
If something is missing create new issue in VCS.

##Installation

1. First download latest [Java JRE](http://java.com/en/download/).
2. Click on application jar file and follow instructions.

##Issue and feature request

Add new [issue in Github VCS](https://github.com/urosjarc/headhunt/issues).

##Usage

###Scraper management

To add new scraper go to [Vimeo API developers](https://developer.vimeo.com/)
and click on `My Apps`. And click on `Create a new app`.
Fill all required fields and click `Create app`. Then click on created
app and click on `Authentication` and after that `Generate Token`.
Application will need the `Your new Access token` string when
new scraper will be added to application. After you get `Access token`.
To to application and follow `File > New... > Scraper`. And fill all
the fields. In query you must put the string or character on which
application will scraper. For example good queryes would be
`a,b,c, ...`. If you pass `a` in query field the scraper will scrape
all users that can be searched with `a` character. To edit or delete
scraper click on `Scrapers` tab and double click on scraper name that
you want to update. After that you can fix the scrapers values.
 
###Database management

You can manage database by exporting and importing.
Follow `File > Database > ...` and select what you would like to do
with the database. On export the application will export all database
to `<appName>.tar.gz` file. With import you can import the same file
that application was exporting.

###Database searching

To search database. Follow `Edit > Find... > ...` and click what you
like to find. Pass the string that you would like to find and be aware
about specijal character `%` which means anything before or after.
See the examples and their explanations.

Examples:

1. `%stop motion%` Will look for `<any-string>stop motion<any-string>`.
2. `stop motion%` Will find for `stop motion<any-string`.
2. `%stop%motion%` Will find for `<any-string>stop<any-string>motion<any-string`.

###Headhunt information

When you make new search query. Items in `Headhunt` tab will update. Double
click on table item and `Headhunt window` will show which will provide
with all user information on one place.
	

