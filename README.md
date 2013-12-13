NBIC-BioAssist: Hands-on Hadoop
===============================
Welcome to the BioAssist Hands-on Hadoop workshop. This session has two objectives:

* Try out/get to know SURFsara's Hadoop offerings and implement some basic code.
* Tackle a bioinformatics problem using Hadoop.

Some handy references to have open:

* Hadoop tutorial: [http://developer.yahoo.com/hadoop/tutorial/index.html](http://developer.yahoo.com/hadoop/tutorial/index.html)
* Cascading documentation: [http://www.cascading.org/documentation/](http://www.cascading.org/documentation/)
* Pig documentation: [http://pig.apache.org/docs/r0.11.1/](http://pig.apache.org/docs/r0.11.1/)

Hadoop access and first job
===========================

Before being able to implement and run or debug any code, we need to get access to a Hadoop environment. For this workshop
we will make use of a virtual machine that is configured both for a standalone Hadoop environment and the SURFsara cluster.

You can retrieve this virtual machine from the following location if you have not already done so:

[http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova](http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova)

In order to use the image you will need to import it into VirtualBox:

1. Start VirtualBox and select `File -> Import Appliance...`.
2. Select the `hadoop-vm.ova` image from the download location.
3. Continue and import the image. This will take around one to two minutes.
4. Once it is imported check the setting for memory - setting it to one to two gigabytes of memory will be better for this session.
5. Once the image has been imported start the virtual machine by selecting it and clicking the start icon. Hit enter at the Grub boot menu to start the os (or wait for the timeout).

The image has been configured with a single user:

username: user
password: hadoop

By default hadoop is configured to connect to the hadoop environment on the localhost. In order to switch to cluster usage 
you can use the switch_hadoop script to toggle between configurations. In order to use the cluster you will first need to authenticate against the cluster.
Cluster authentication uses Kerberos and different account details. You can claim a user and password for the Hadoop cluster here (just add your name and/or e-mailaddress after the acount):

[https://etherpad.conext.surfnetlabs.nl/p/BioAssist:%20Hands-on%20Hadoop](https://etherpad.conext.surfnetlabs.nl/p/BioAssist:%20Hands-on%20Hadoop)

Initializing Kerberos and authenticating is a matter of issuing the following command (where user is your hadwsXX name):
 
	~$ kinit <user>
	<user>@ALLEY.SARA.NL's Password: 
	
If you wish to access the cluster from your local machine, you can follow the instructions here:

[https://grid.sara.nl/wiki/index.php/Using_Alley_from_your_local_machine](https://grid.sara.nl/wiki/index.php/Using_Alley_from_your_local_machine)

Hello world: Counting words
---------------------------
Before we start counting words we will need some input data on HDFS. For this we can use the hadoop fs shell:

[http://archive.cloudera.com/cdh/3/hadoop-0.20.2-cdh3u6/file_system_shell.html](http://archive.cloudera.com/cdh/3/hadoop-0.20.2-cdh3u6/file_system_shell.html)

Browse the home folder on your local hadoop environment: `hadoop fs -ls /user/user`

The workshop VM contains some data to use during the session. The '~/Data/' directory contains some text data and some sequencing reads. These need to be uploaded to 
HDFS: 

1.	Create a folder in HDFS for your input data called 'mapreduce/input'. Notice that you do not have to create the' mapreduce' folder first. This is different on a normal Unix filesystem.
2.	Upload the data file 'sample.txt' and 'alice.txt' to the input folder. Verify the upload by using the `hadoop fs -ls` and `hadoop fs -cat` commands.
3.	Rename the file to 'mapreduce/input/alice.txt' without uploading it from the local filesystem again.
4.	Download a file from the HDFS to the local filesystem.

Now you are ready to run an existing example:

You submit a job to the MapReduce framework using the 'hadoop jar' command. You will run an example program shipped with the Hadoop distribution on your input file.

1.	Run `hadoop jar $HADOOP_HOME/hadoop-examples-0.20.2-cdh3u6.jar`. Read the usage instructions: we are interested in the WordCount example.
2.	Run the WordCount example on the input file you uploaded before. Use 'mapreduce/output/wc-example' as the output path.
3.	Look at the output generated, especially the Counters of the MapReduce Framework.
4.	Inspect the output of the program. The results of the reducer(s) are stored in the 'part-N' files. An easy way to look at the output is `hadoop fs -cat mapreduce/output/wc-example/part-* | less`.

When you run the example again with the same output path Hadoop will refuse to run the job. Remove the output path with `hadoop fs -rmr` or specify a different one. You can check the
status of the jobs by pointing your browser to the jobtracker (the browser in the VM contains some bookmarks).

Time to code: Kmer counting
===========================
Code for this practical is available on github. Clone the repository to get all the projects:

	git clone https://github.com/vmk/NBIC-BioAssist--hands-on-Hadoop

The repository contains two sections:

1. part1 - some example wordcount code as inspiration for part2
	* wordcount_mr - wordcount using the mapreduce api
	* wordcount_cascading - wordcount using the cascading api
	* wordcount_pig - wordcount using pig
2. part2 - skeleton code to be implemented
	* kmers_cascading - implement base counting and kmer counting in cascading here
	* kmers_mr - implement base counting and kmer counting using mapreduce here
	* kmers_pig - and example kmer counting using pig and a user defined function
	* biopig - the biopig package for you to experiment with
	
All the mr and cascading directories have an eclipse project file present and the VM has a version of eclipse installed - stat it by entering `eclipse` in the terminal.
You can simply import projects into eclipse via `File -> Import...` and then selecting `General -> Existing projects into workspace`. In addition to the code external libraries are 
included as well as ant build files. To use the build files select `Window -> Show view -> Ant` in the view that pops up build files can be added by clicking the small plus in the toolbar. Each
project typicaly has one clean target and one or two jar targets which will produce the desired jarfiles to run on hadoop.

Easing into it: more wordcount
------------------------------
First start by studying the wordcount examples from the part1 folder. These are complete programs that you can run on Hadoop. Try to build each of them with the supplied 
buildfiles and run them on the 'sample.txt' and 'alice.txt' text. Input and output paths need to be specified on the commandline e.g.:

	`hadoop jar wordcount_mr.jar <input hdfs path> <output hdfs path>`
	
Note that the paths can include wildcards: in case of multiple files matching the wildcards these will all be streamed through your mappers and reducers. Use the jobtracker web interface to examine your running jobs (bookmarks in browser VM). Once you have tried this
on the local Hadoop on the VM you can switch to the cluster by issuing the `switch_hadoop` command. Upload one of the txt's to your HDFS home (/user/hadwsXX/ on the cluster) and run the wordcount there. Once you have done this
issue the `switch_hadoop` command again to switch back to local mode for the next exercise.

Extending wordcount: basecount
------------------------------
Alright, time to do some programming of our own: extend the wordcount example so that it now counts letters, or bases in a sequence. Sequence reads from e. coli are provided in
the '~/Data' directory. Upload this file to your home folder on the local or VM Hadoop instance. For debugging it is probably wise to upload a much smaller sample to test on. Create a 
smaller version with the following command and upload the resulting smaller file:

	zcat ecoli_ref-5m.fastq.gz | head -n 32 | gzip > ecoli.small.fastq.gz
	
Now open either the mapreduce or cascading skeleton code and implement the missing code (see the comments). Once you are satisfied, build a jar file using the build file and run it on the small
file first. Note that it is not necessary to handle the compression of the files yourself. This is handled by Hadoop for you, but has one drawback: gzip files are not splittable so you will only get assigned one
mapper per file. Once the count works on the small file you can try it on the complete file or on data on the hadoop cluster (use switch_hadoop to switch). The cluster data
is located in the /data/public/sequences directory. There you can find the e. coli data and around 860 million reads of a human genome. Both files are present in the zipped (unsplittable form) or in a splittable form (splittable in the filename). You can see 
the difference this makes by running basecount on the gzip and splittable version of the e. coli data (we'll leave the human data alone, for now).   

Extending basecount: kmercount
------------------------------
Basecounting is actually kmercounting where k=1. In kmercounting we count all the n-grams or n-tuples of size k in a sequence. Take the sequence:
	
	ATTCGA

The kmers for k=3 then equal:
	
	ATT
	 TTC
	  TCG
	   CGA
	   
Some skeleton code is already provided: implement it so that the code reads in a (set of) reads, a value for k and produces these kmers as key and count as value. Run it first on the small e. coli data
on your local VM. Once you are satisfied you can switch to the cluster and run it on all human data (use a wildcard in the path) or even all the data in the sequence directory. Do take note
to use the splittable version for large runs (or you will be stuck with a single mapper per file which will take quite long). In order to verify your results we implemented a kmer count 
in pig . You can run it from the kmers_pig folder by issuing the command:

	pig -f kmer.pig -param input=/hdfs/path

The kmercounts will be printed to stdout (perhaps best to not do this on the full human data..)

Above and beyond: Doing something interesting
=============================================
Having implemented a basic kmercounter we can move on and analyze the sequences in more detail. Here are some things you could try (this will probably require additional map and reduce steps for you to implement/or more pipes in cascading):

1. Try and reproduce the results here [http://www.ncbi.nlm.nih.gov/pmc/articles/PMC3680041/table/T1/](http://www.ncbi.nlm.nih.gov/pmc/articles/PMC3680041/table/T1/). The human reads on the cluster
are from subject HG02057 from the 1000 genomes project.
2. Try to estimate genome size for both e. coli and the human data. 
3. Play around with biopig - biopig can be cloned from github and has some great examples. Please see:

	[https://github.com/JGI-Bioinformatics/biopig](https://github.com/JGI-Bioinformatics/biopig) 
	[http://bioinformatics.oxfordjournals.org/content/early/2013/09/10/bioinformatics.btt528](http://bioinformatics.oxfordjournals.org/content/early/2013/09/10/bioinformatics.btt528).

--------------------
Both 1 and 2 will require you to further add code to count how many kmer matches exist genomewide (i.e. bin kmers who exist once and count these; bin k-mers with frequency 2 and count these etc.) - a good reference that explains this (and a good blog overall):

[http://www.homolog.us/blogs/blog/2011/09/20/maximizing-utility-of-available-rams-in-k-mer-world/](http://www.homolog.us/blogs/blog/2011/09/20/maximizing-utility-of-available-rams-in-k-mer-world/)

To estimate genome size, 2, you can calculate the kmer frequency within your read data. Meaning you chop all of the reads you've generated up in to kmers (a kmer of 17 is the most common, as it is long enough to yield fairly specific sequences (meaning that its unlikely the kmer is repeated throughout the genome by chance), but short enough to give you lots of data). You then count the frequency with which each 17-mer represented by your data is found among all of the reads generated and create a frequency histogram of this information. For non-repetitive regions of the genome, this histogram should be normally distributed around a single peak (although in real data you will have a asymptote near 1 because of rare sequencing errors etc). This peak value (or peak depth) is the mean kmer coverage for your data. 

You can relate this value to the actual coverage of your genome using the formula M = N * (L – k + 1) / L, where M is the mean kmer coverage, N is the actual coverage of the genome, L is the mean read length and k is the kmer size.

L - k + 1 gives you the number of kmers created per read. 

So basically what the formula says is the kmer coverage for a genome is equal to the mean read coverage * the number of kmers per read divided by the read length.

Because you know L (your mean read length) and k (the kmer you used to estimate peak kmer coverage) and you've calculated M, you simply solve the equation for N as:

N = M / (( L - k + 1 )/ L ) and calculate N.

Once you have that, divide your total sequence data by N and you have your genome estimate.






