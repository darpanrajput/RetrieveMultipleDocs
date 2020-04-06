# RetrieveMultipleDocs 
### This is available in PaginationAtivity
This Project is connected to Firestore To make use of Queries
<br>this to provide the efficient way of iterating the collection and finding the doc where the change occur
<br>instead of iterating the whole the documents
<b>in this onStart method we usually fetching the whole  list of documents when anything changes in collection
<br> but it is not one of the most efficient way of doing it. the best way of doing is to only provide the updated
<br>version of that document that has been recently changed instead of fetching the whole list if documents
<br>here we are iterating through something called document change get documents changes to change the required object this means we dont need to go on whole dat snapshot every document the document changes at the
<br> there are three type of document changes that will provide the the doc object will is changes 
#### ADDED, MODIFIED, REMOVED
