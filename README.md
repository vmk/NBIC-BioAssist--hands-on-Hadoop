NBIC-BioAssist: Hands-on Hadoop
===============================
Welcome to the BioAssist Hands-on Hadoop workshop. This session has two objectives:

* Try out/get to know SURFsara's Hadoop offerings and implement some basic code.
* Implement a bioinformatics proble.

Hadoop access: Setting up your environment
------------------------------------------

Before being able to implement and run or debug any code, we need to get access to a Hadoop environment. For this workshop
we will make use of a virtual machine that is configured both for a standalone Hadoop environment and the SURFsara cluster.

You can retrieve this virtual machine from the following location if you have not already done so:

[http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova](http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova)

In order to use the image you will need to import it into VirtualBox:

1. Start VirtualBox and select `File -> Import Appliance...`.
2. Select the `hadoop-vm.ova` image from the download location.
3. Continue and import the image. This will take around one to two minutes.
4. Once the image has been imported start the virtual machine by selecting it and clicking the start icon. Hit enter at the Grub boot menu to start the os (or wait for the timeout).

The image has been configured with a single user:

username: user 

password: hadoop

By default hadoop is configured to connect to the hadoop environment on the localhost. In order to switch to cluster usage 
you can use the switch_hadoop script to toggle between configurations. In order to use the cluster you will first need to authenticate against the cluster.
Cluster authentication uses Kerberos and different account details. You can claim a user and password for the Hadoop cluster here (just add your name and/or e-mailaddress after the acount):

[https://etherpad.conext.surfnetlabs.nl/p/BioAssist:%20Hands-on%20Hadoop](https://etherpad.conext.surfnetlabs.nl/p/BioAssist:%20Hands-on%20Hadoop)

Initializing Kerberos and authenticating then is a matter of issuing the following command (where user is your hadwsXX name):
 
	~$ kinit <user>
	<user>@ALLEY.SARA.NL's Password: 
	
If you wish to access the cluster from your local machine, you can follow the instructions here:

[https://grid.sara.nl/wiki/index.php/Using_Alley_from_your_local_machine](https://grid.sara.nl/wiki/index.php/Using_Alley_from_your_local_machine)

Hello world: Counting words
---------------------------
Before we start counting words we will need some input data on HDFS. For this we can use the hadoop fs shell:

[http://archive.cloudera.com/cdh/3/hadoop-0.20.2-cdh3u6/file_system_shell.html](http://archive.cloudera.com/cdh/3/hadoop-0.20.2-cdh3u6/file_system_shell.html)

Browse the home folder on your local hadoop environment: 'hadoop fs -ls /user/user'

The workshop VM contains some data to use during the session. The '~/Data/' directory contains some text data and some sequencing reads. These need to be uploaded to 
HDFS: 

1.	Create a folder in HDFS for your input data called 'mapreduce/input'. Notice that you do not have to create the' mapreduce' folder first. This is different on a normal Unix filesystem.
2.	Upload the data file 'sample.txt' and 'alice.txt' to the input folder. Verify the upload by using the 'hadoop fs -ls' and 'hadoop fs -cat' commands.
3.	Rename the file to 'mapreduce/input/alice.txt' without uploading it from the local filesystem again.
4.	Download a file from the HDFS to the local filesystem.

Now you are ready to run an existing example:

You submit a job to the MapReduce framework using the 'hadoop jar' command. You will run an example program shipped with the Hadoop distribution on your input file.

1.	Run 'hadoop jar $HADOOP_HOME/hadoop-examples-0.20.2-cdh3u6.jar '. Read the usage instructions: we are interested in the WordCount example.
2.	Run the WordCount example on the input file you uploaded before. Use 'mapreduce/output/wc-example' as the output path.
3.	Look at the output generated, especially the Counters of the MapReduce Framework.
4.	Inspect the output of the program. The results of the reducer(s) are stored in the 'part-N' files. An easy way to look at the output is 'hadoop fs -cat mapreduce/output/wc-example/part-* | less'.
5.	Upload the file 'alice.txt' as 'mapreduce/input/large.txt' and also run the WordCount program in this input file.

When you run the example again with the same output path Hadoop will refuse to run the job. Remove the output path with 'hadoop fs -rmr' or specify a different one. You can check the
status of the jobs by pointing your browser to the jobtracker (the browser in the VM contains some bookmarks).



Time to code: Kmer counting
---------------------------

Above and beyond: Doing something interesting
---------------------------------------------




