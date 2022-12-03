# Project Report

## Team Members

### Aanand Shah (as223892) and Davon Davis (dld3349)

## Intro

#### PDFMill is an app built for filling, signing, and exporting PDFs from a mobile device.

#### Home Screen
![image](https://user-images.githubusercontent.com/77517056/205418373-39af7f79-1b60-4042-8f93-a634fe0d61ff.png)
#### Empty form to fill in data
![image](https://user-images.githubusercontent.com/77517056/205418425-ba04818f-c2de-45c2-a130-01c95bebb15a.png)
#### Fill out form completely
![image](https://user-images.githubusercontent.com/77517056/205418447-71b21044-ed3e-41a0-adc2-0951fd1a5067.png)
#### Screen that requires your signature
![image](https://user-images.githubusercontent.com/77517056/205418474-f3ae0aae-4a09-4699-8bca-b5288d78ddc7.png)
#### Screen that displays toast saying that the PDF has been saved
![image](https://user-images.githubusercontent.com/77517056/205418494-3de9e6f0-f6ec-4c5d-a0c2-a726888c11e2.png)
#### View confirming that the PDF file has been saved to the User's GoogleDrive
![image](https://user-images.githubusercontent.com/77517056/205418532-bac282d1-92d6-4f0d-b194-0aeab5e3de3b.png)


### APIs

Our app is heavily reliant on APIs, and this was a large part of our primary effort. We wanted to
build something which integrates with existing services that are used in the space; augmenting them
instead of rehashing existing functionality in a clunkier way. The way we were thinking about it
was: all of these functions and services exist, but they need to be glued together. That's what
PDFMill offers.

We used:

- Google Auth
- Google Drive

### Design Patterns

We did our best to stick to out-of-the-box, intuitive Android functionality in building the flow of
the app. We want the process of fill -> sign -> export to be as simple as possible, and we found the
best way to do this was via sequential Fragments and Navigation. We didn't use any UI components too
extensively since our app is heavily backend-based.

### Third Party Libraries

#### Apache PDFBox (https://pdfbox.apache.org/)

This is a very popular Java library commonly used for manipulating PDFs programmatically. Nowadays,
with many PDF documents, you are only able to use some of the advanced functionality like filling
and digitally signing (and even printing) if you use Adobe's proprietary Reader software. We found
this to be hugely unfriendly to the user, and appreciate that PDFBox unlocks the ability to parse a
file for fields, change the value of the fields, and even lock the PDF to prevent further edits.

This library is a bit challenging to use since multiple parts of the website are under
construction (and seem to have been that way for a while). Further, it's got a lot of
library-specific Java classes which a programmer would not usually be used to seeing or working
with; so it can be hard to know what function to implement and where. Drawing in a field is also
tricky -- you can see in the DA4856Service some slightly complicated code to add a bitmap to a field
while scaling it down as well.

#### Android Signature Pad

This library allows for a user's input to be mapped to a Bitmap or an SVG. It's very useful since it
comes prepackaged as a <SignaturePad />UI component, and only has three internal functions that need
to be overridden.

This library did not present many challenges, perhaps because it is so skinny. The only confusing
portion came when implementing the save and cancel buttons; It was not immediately clear whether
this was supposed to come with the signature pad or if those were meant to be added by the
developer. After poking through the source code, we found it to be the latter.

### Third Party Services

#### Google Auth/Drive

We are bundling these two together because they work hand-in-hand, thankfully. We added Google Auth
to help the user feel more secure about their PDFs, a lot of which contain sensitive information
about their performance or duty. This coupled nicely with the Drive SDK and API, which allowed us to
use the same Auth token and login to send accredited requests to Google in order to upload the
completed PDF. We chose to use these instead of creating our own bespoke login service because we
saw this as the best MVP (Minimum Viable Product) to get a large slice of the market on board. Users
would be able to have secure sign-in and document storage simply by having a Google account; they
would no longer have to worry about whether a specific signed or unsigned version of a document is
available on their device. It was challenging to implement Google Auth at first because it meant
starting from scratch in Firebase and figuring out what the setup steps were, and how they differed
from the steps we took in previous FC's and homeworks. But thankfully the docs were robust enough
that we were able to get it working without too much reshuffle.

### Noteworthy Code

We recommend checking out the DA4856Service, as well as the SignaturePadFragment. Both of these show
examples of blending brand-new, third-party libraries with existing frameworks and techniques that
we picked up throughout the semester. Particularly interesting was the way we bundled the PDF
template file as a resource for the project, read it in as a stream, and then closed it. Also
interesting is the scaling we did for the signature -- not everyone will fill up their signature
pad, and we needed to be able to scale it appropriately to fit in the skinny signature space on the
PDF. To do so, we used some simple cross-multiplication to bring it down to size. 

 Discuss the most important or interesting thing you learned doing your
project.
 The most important thing I learned was the utilization of PDFBox. The reason why it is the most important
thing that I have learned is due to my frequent use of filling out and distributing the same pdf. Its very convenient 
because I don't actually have to find time to get a computer to fill one out

Discuss the most difficult challenge you overcame and/or your most interesting debugging
story.
The most difficult challenge that I overcame was finding the right resource to implement uploading to 
Google Drive. There are so many different ways to implement it, that it took more time than I would've
like to.

If necessary, briefly tell us how to build and run your project. 
In order to run the project you must have an Android emulator setup.

Include details about how to set back end services (if you use them). In the common case, we will rely on your demo, but just in case we have an issue, we'd like some tips. For masters students, please submit by zipping your
project and submitting to canvas.
n/a

#### Lines of code
XML - 576
Kotlin - 454

#### Code Frequency
![image](https://user-images.githubusercontent.com/77517056/205419214-31ba1963-cd72-4a6a-b4a2-9e20175414b6.png)

Submit your final code to this new project
