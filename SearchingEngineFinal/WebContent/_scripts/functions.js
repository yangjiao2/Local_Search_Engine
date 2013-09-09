// Begin jQuery functions
$(function() {
    $('#btnSearch').click(function() {
        Search(0);
    });

    // Replace the following string with the AppId you received from the
    // Bing Developer Center.
	// Download by http://www.jb51.net
    var url = "MainServlet?";
    var Query = "Query="
    var Page = "&Page="
    var xmlHttpRequest;
    var nownub = 0;
    var totalpage = 0;
    function Search(pagenub) {

        var searchTerms = $('#txtQuery').val();
        var requestStr = url + Query + searchTerms + Page +pagenub;
        nownub = pagenub;
        if(window.XMLHttpRequest) 
        {
			 xmlHttpRequest=new XMLHttpRequest();
        }
        else if(window.ActiveXObject)
        {
       	 xmlHttpRequest=new ActiveXObject("Microsoft.XMLHttp");
        }
		 

		xmlHttpRequest.onreadystatechange = DisplayResults;
		xmlHttpRequest.open("GET", requestStr, true);  
		xmlHttpRequest.send(null);
		
    }

    
    function DisplayResults() {
        
    	var response = xmlHttpRequest.responseText;
    	var results = response.split("!");
//    	alert(response);
        $("#result-list").html("");                                 // Clear our existing results
        $("#result-navigation li").filter(".nav-page").remove();    // Remove our navigation
        $("#result-aggregates").children().remove();                // Remove our hit information

        //var results = response.SearchResponse.Web.Results;          // Define our web results in a more succinct way

        // Let the user know what they searched for and what the search yielded
        $('#result-aggregates').prepend("<p>Query："+$('#txtQuery').val()+" </p>");
        $('#result-aggregates').prepend("<p id=\"result-count\">Total:" + results[0] + "</p>");

        // Create the list of results
        var link = [];                                  // We'll create each link in this array
        var regexBegin = new RegExp("\uE000", "g");     // Look for the starting bold character in the search response
        var regexEnd = new RegExp("\uE001", "g");       // Look for the ending bold character in the search response
        var totalnub = 0;
        if(parseInt(results[0]) < 20)
        	totalnub = parseInt(results[0])+1;
        else
        	totalnub = 21;
        totalpage = parseInt(results[0])/20;
        for (var i = 1; i < totalnub; ++i) {
            // Step through our list of results and build our list items
        	var temp = results[i].split("|");
        	
            link[i] = "<li><a href=\"" + temp[1] + "\" title=\"" + temp[0] + "\">"
                + temp[0] + "</a>"
                + "<p>" + temp[2] + "<p>"
                + "<p class=\"result-url\">" + temp[1] + "</p></li>";

            // Replace our unprintable bold characters with HTML
            link[i] = link[i].replace(regexBegin, "<strong>").replace(regexEnd, "</strong>");
        }
        $("#result-list").html(link.join(''));          // Concatenate our list and add it to our page


        // Set up our result page navigation 
        CreateNavigation(10, 100);
    }

    function StartOffset(results) {
        if (WebOffset == 0) {
            return 1;
        }
        else {
            return (WebOffset * results.length) + 1;
        }
    }

    function EndOffset(results) {
        if (WebOffset == 0) {
            return results.length;
        }
        else {
            return (WebOffset + 1) * results.length;
        }
    }

    function CreateNavigation(totalHits, pageSize) {
        var totalPages = (totalHits / pageSize > 10) ? 10 : parseInt(totalHits / pageSize);
        var nav = [];
        for (var i = 0; i < totalPages; i++) {
            nav[i] = "<li class=\"nav-page\">" + (i + 1) + "</li>";
        }
        $("#result-navigation li:first").after(nav.join(''));

        // Create a listener for the page navigation click event
        $("#result-navigation li.nav-page").click(function() {
            WebOffset = parseInt($(this).html()) - 1;
            Search();
        });

        // Show the navigation!
        $("#result-navigation").show();
    }

    $("#prev").click(function() {
        if (nownub > 0) Search(nownub-1);
    });

    $("#next").click(function() {
        if(nownub<totalpage)Search(nownub+1);
        
    });

    function DisplayErrors(errors) {
        var errorHtml = [];

        for (var i = 0; i < errors.length; ++i) {
            errorHtml[i] = "<li>" + errors[i] + "</li>";
        }
        $('#error-list').append(errorHtml.join(''));
    }
});
