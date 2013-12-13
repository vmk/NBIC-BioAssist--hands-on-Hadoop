NBIC-BioAssist: hands-on-Hadoop - 13-12-2013
============================================
Welcome to the BioAssist hands-on Hadoop workshop. This session has two objectives:

1.) Try out/get to know SURFsara's Hadoop offerings and implement some basic code.
2.) Implement a bioinformatics proble.

Hadoop access: Setting up your environment
------------------------------------------

Before being able to implement and run or debug any code, we need to get access to a Hadoop environment. For this workshop
we will make use of a virtual machine that is configured both for a standalone Hadoop environment and the SURFsara cluster.

You can retrieve this virtual machine from the following location if you have not already done so:

[http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova](http://beehub.nl/surfsara-hadoop/public/nbic-hadoop-vm.ova)

n order to use the image you will need to import it into VirtualBox:

1.) Start VirtualBox and select `File -> Import Appliance...`.
2.) Select the `hadoop-vm.ova` image from the download location.
3.) Continue and import the image. This will take around one to two minutes.
4.) Once the image has been imported start the virtual machine by selecting it and clicking the start icon. Hit enter at the Grub boot menu to start the os (or wait for the timeout).

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

Time to code: Kmer counting
---------------------------

Above and beyond: Doing something interesting
---------------------------------------------




