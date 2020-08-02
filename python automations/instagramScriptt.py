######                                          ATTENTION                                              ######
# !!!! YOU NEED TO FULLSCREEN THE CHROME AFTER IT OPENS BY ITSELF !!!! OTHERWISE THIS CODE WON'T WORK !!!!!!!

#Instagram Script

#----------------------------------------------------------------------------------------------------------------------

# This program scrapes user's Instagram followers and followings. Prints the followers, followings. Searchs if a user
# is following you or if you are following a user.

#version 2.0

#author: Ahmet Turhan (qgood)

#since: 31.07.2020

from selenium import webdriver
import pyautogui as pya
import time
import sys
import shutil

programTitle = 'Instagram Script v2.0' #program title

username = pya.prompt(text='Enter your Instagram username: ', title=programTitle , default='') #ask user to enter usrname
password = pya.password(text='Enter your instagram password', title=programTitle , default='', mask='*') #ask user to enter pswd



#you can enter your login credentials manually if you want
#username = 'usr'
#password = 'pswd'

browser = webdriver.Chrome() #open chrome window
browser.get('https://instagram.com') #go to instagram

time.sleep(2) #wait 2 secs to ensure the page is loaded

usernameBox = browser.find_element_by_xpath(
    '/html/body/div[1]/section/main/article/div[2]/div[1]/div/form/div[2]/div/label/input') #enter username to username field
usernameBox.send_keys(username) #write username to the field

passwordBox = browser.find_element_by_xpath(
    '/html/body/div[1]/section/main/article/div[2]/div[1]/div/form/div[3]/div/label/input') #enter password to password field
passwordBox.send_keys(password) #write password to the field

loginButton = browser.find_element_by_xpath(
    '/html/body/div[1]/section/main/article/div[2]/div[1]/div/form/div[4]/button').click() #click login button

time.sleep(5) #wait 5 secs to ensure the webpage is fully loaded

notNowButton = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/div/div/div/button').click() #click to not now button
time.sleep(2) #you know what this means

otherNotNowBtn = browser.find_element_by_xpath('/html/body/div[4]/div/div/div/div[3]/button[2]').click() #click to the other not now button
time.sleep(2) #i'm not writing comments to time.sleep() anymore...

profilePic = browser.find_element_by_xpath(
    '/html/body/div[1]/section/nav/div[2]/div/div/div[3]/div/div[5]/span/img').click() #click to profile pic to go to users profile
time.sleep(0.5)

userProfile = browser.find_element_by_xpath(
    '/html/body/div[1]/section/nav/div[2]/div/div/div[3]/div/div[5]/div[2]/div/div[2]/div[2]/a[1]/div').click() #click profile section
time.sleep(1.5)

followers = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span').text #get follower number
following = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span').text #get following number

followers = int(followers) #cast follower number to int in order to use it in a for loop
following = int(following) #same thing above


def saveToTxt(followersList, followingList, username):
    fileNameFollowers = 'Followers '+username+'.txt'
    fileNameFollowing = 'Following '+username+'.txt'

    f = open(fileNameFollowers, 'w')
    for ele in followersList:
        f.write(ele + '\n')

    f.close()

    fo = open(fileNameFollowing, 'w')
    for ele in followingList:
        fo.write(ele + '\n')

    fo.close()

    usCon = pya.confirm(text='Your followers and following saved succesfully. Do you want to move it to desktop?',
                        title=programTitle, buttons=['Yes', 'No'])
    if usCon == 'Yes':
        original = rf'C:\Users\Ahmet Faruk Turhan\PycharmProjects\Instagram Script'+'\Followers '+username+'.txt'
        target = rf'C:\Users\Ahmet Faruk Turhan\Desktop'+'\Followers '+username+'.txt'
        shutil.move(original, target)

        original = rf'C:\Users\Ahmet Faruk Turhan\PycharmProjects\Instagram Script'+'\Following '+username+'.txt'
        target = rf'C:\Users\Ahmet Faruk Turhan\Desktop'+'\Following '+username+'.txt'
        shutil.move(original, target)
    else:
        programMenu(followersList, followingList, username)
def programMenu(followersList, followingList, username): #main menu of the program, asks user to what to do
    usInp = pya.confirm(text='What do you want to do now?', title=programTitle, buttons=['Save',
                                                                                         'Search in your following/followers', 'Exit', 'Main Menu']) #ask the user
    if usInp == 'Search in your following/followers':
        confirmUs = pya.confirm(text='Search in your following or followers?', title=programTitle, buttons= ['Followers', 'Following']) #ask the user

        if confirmUs == 'Followers': #search in followers
            usernameInp = pya.prompt(text='Enter the username that you want to search in your followers list',
                                     title=programTitle) #ask user to enter a username to search

            if usernameInp in followersList: #check if that username exists in the list
                pya.alert(text=usernameInp + ' is following you.', title=programTitle, button='Ok') #notify the user
            else:
                pya.alert(text=usernameInp + ' is not following you.', title=programTitle, button='Ok') #notify the user
            programMenu(followersList, followingList, username) #go to main menu

        elif confirmUs == 'Following': #search in following
            usernameInp1 = pya.prompt(text='Enter the username that you want to search in your followings:',
                                      title=programTitle)

            if usernameInp1 in followingList: #check if entered username exists in the list
                pya.alert(text='You are following ' + usernameInp1, title=programTitle, button='Ok') #notify the user
            else:
                pya.alert(text='You are not following ' + usernameInp1, title=programTitle, button='Ok') #notify the user
            programMenu(followersList, followingList, username) #go to main menu

    elif usInp == 'Save': #save followers and following to a text file
        saveToTxt(followersList, followingList, username)
        programMenu(followersList, followingList, username)

    elif usInp == 'Main Menu':
        mainMenu(username)
    else: #exit button, exit the program
        sys.exit('You choosed to exit the program') #exit the program with a messsage

def scrapeFollowers(followers, followersList): #a method to get the followers usernames
    followersBtn = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/header/section/ul/li[2]/a').click() #click to followers
    time.sleep(0.7)

    pya.click(1007, 638) #click to center in order to enable scrolling in the followers box, this is why you need to fullscreen the chrome
    time.sleep(0.1)

    calculation = ((followers - 6) / 6) + 10 #magic calculation to optimize the scrolling
    calculation = int(calculation) #cast it to int to use it in a for loop

    for i in range(0, calculation): #scrolling to the bottom
        pya.scroll(-1000)
        time.sleep(0.3)

    counter = 1 #a counter to get the next item everytime. Otherwise it will only store one username.
    for c in range(1, followers + 1):
        followersXpath = f'/html/body/div[4]/div/div/div[2]/ul/div/li[{counter}]/div/div[1]/div[2]/div[1]/span/a' #next item everytime
        tempName = browser.find_element_by_xpath(followersXpath).text #store the current username to a temp variable

        followersList.append(tempName) # add username to the list
        counter = counter + 1 #increment the counter

    closeBtn = browser.find_element_by_xpath('/html/body/div[4]/div/div/div[1]/div/div[2]/button').click() #click to  the x button

def scrapeFollowing(following, followingList): #same function above. Gets following instead of followers
    followingBtn = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/header/section/ul/li[3]/a').click() #click following button
    time.sleep(0.7)

    pya.click(1007, 638) #click to center in order to enable scrolling in the followers box, this is why you need to fullscreen the chrome
    time.sleep(0.1)

    calculation = ((following - 6) / 6) + 10 #this was commented above
    calculation = int(calculation)

    for i in range(0, calculation): #same thing in scrapeFollowers
        pya.scroll(-1000)
        time.sleep(0.3)
    counter = 1

    for c in range(1, following + 1):
        xPathh = f'/html/body/div[4]/div/div/div[2]/ul/div/li[{counter}]/div/div[1]/div[2]/div[1]/span/a' #next item everytime
        tempName = browser.find_element_by_xpath(xPathh).text

        followingList.append(tempName)
        counter = counter + 1
    closeBtn = browser.find_element_by_xpath('/html/body/div[4]/div/div/div[1]/div/div[2]/button').click() #click to the x button

time.sleep(1)

def mainMenu(username):
    followersList = []  # create a list to store the followers
    followingList = []  # create a list to store the followings
    usIn = pya.confirm(text='What do you want to do?', title=programTitle,
                       buttons=['Scrape', 'Follow/Unfollow', 'Search', 'Exit'])
    if usIn == 'Scrape':
        scrapeFollowing(following, followingList)  # call the function to get the following
        time.sleep(1)  # just making sure
        scrapeFollowers(followers, followersList)  # call the function to get the followers
        programMenu(followersList, followingList, username)

    elif usIn == 'Search':
        username = pya.prompt(text='Enter the username that you want to search:', title=programTitle)
        clickToSearchBox = browser.find_element_by_xpath(
            '/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div').click()
        time.sleep(0.3)
        searchBox = browser.find_element_by_xpath('/html/body/div[1]/section/nav/div[2]/div/div/div[2]/input')
        searchBox.send_keys(username)
        time.sleep(1)
        result = browser.find_element_by_xpath(
            '/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[3]/div[2]/div/a[1]').click()
        time.sleep(5)

        newFollowers = browser.find_element_by_xpath(
            '/html/body/div[1]/section/main/div/header/section/ul/li[2]/a/span').text  # get follower number
        newFollowing = browser.find_element_by_xpath(
            '/html/body/div[1]/section/main/div/header/section/ul/li[3]/a/span').text  # get following number
        newFollowers = newFollowers.replace('.', '')
        newFollowing = newFollowing.replace('.', '')
        newFollowers = int(newFollowers)
        newFollowing = int(newFollowing)
        newFollowersList = []
        newFollowingList = []
        scrapeFollowers(newFollowers, newFollowersList)
        time.sleep(1)
        scrapeFollowing(newFollowing, newFollowingList)
        time.sleep(0.5)
        programMenu(newFollowersList, newFollowingList, username)
    elif usIn == 'Follow/Unfollow':
        username = pya.prompt(text='Enter the username that you want to search:', title=programTitle)
        clickToSearchBox = browser.find_element_by_xpath(
            '/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div').click()
        time.sleep(0.3)
        searchBox = browser.find_element_by_xpath('/html/body/div[1]/section/nav/div[2]/div/div/div[2]/input')
        searchBox.send_keys(username)
        time.sleep(1)
        result = browser.find_element_by_xpath(
            '/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[3]/div[2]/div/a[1]').click()
        time.sleep(5)

        isFollow = pya.confirm(text='Follow or Unfollow '+username+' ?', title=programTitle, buttons= ['Follow', 'Unfollow', 'Cancel'])
        if isFollow == 'Unfollow':
            unfBtn = browser.find_element_by_xpath(
                '/html/body/div[1]/section/main/div/header/section/div[1]/div[2]/div/span/span[1]/button').click()
            time.sleep(0.3)
            confrmBtn = browser.find_element_by_xpath('/html/body/div[4]/div/div/div/div[3]/button[1]').click()
            pya.alert(text='Unfollowed ' + username + ' succesfully.', title=programTitle, button='Ok')
            time.sleep(0.7)
        elif isFollow == 'Follow':
            followBtn = browser.find_element_by_xpath('/html/body/div[1]/section/main/div/header/section/div[1]/div[1]/div/span/span[1]/button').click()
            pya.alert(text='Followed '+ username+ ' succesfully', title= programTitle, button= 'Ok')
            time.sleep(0.5)
        else:
            print('cancelled')

        mainMenu(username)
    else:
        sys.exit('You choosed to exit the program.')
mainMenu(username)





