# RetrieveMultipleDocs
This Project is connected to Firestore To make use of Queries branch 3<br>
Add, delete and update operation are the few basic operation performed on the data base but you might come to<br>
a situation where you want to do it not only the small data set but on the large dataset so that’s why we require the batch <br>write  for this to perform the batch operation. <br>
This method is available at OnCreate() Execute the batch write. <br>
The one 0f the major advantages is that the batch operation are performed atomically<br>

here we are calling failure listener as performing operation on documents that does exist<br>
will give an exception and if exception occur that means the all the<br>
above operation will not be executed as the batch is atomic in nature so the note will not be<br>
updated niether the new note will be created nor the the note will be modified<br>
the series of operation will failed if exception occur in any of the batched write operation <br>
#### If you recieve an exception then it will be shown on the textview like here we try to get the document id which does not exist in data base
<img 
src=https://1.bp.blogspot.com/-6lXdHwwqcAE/XosoeN0MCBI/AAAAAAAAB1o/4QxIyL6GPGsDxO-GM_K7oa6u1iO61QqVQCLcBGAsYHQ/s1600/Screenshot_2020-04-06-18-17-51-977_com.darpan.retrievemultipledocs.jpg
width="50%"/>
