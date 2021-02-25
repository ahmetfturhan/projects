#author: Ahmet Faruk Turhan
#The area should be visible on the screen, drag feature is not added yet.
#selenium and chrome webdriver needs to be downloaded
#enter your username and password into the MAIL HERE and PASSWORD HERE
import selenium
from selenium import webdriver
from time import *
from selenium.webdriver.chrome.options import Options
import pyautogui as pya
import random

programTitle = "PixelPlaceBot v1.0"

chrome_options = Options()
chrome_options.add_argument("--start-maximized")
browser = webdriver.Chrome(chrome_options=chrome_options)

usrname = "MAIL ADDRESS HERE"
passwrd = "PASSWORD HERE"

browser.get("https://pixelplace.io/7-pixels-world-war")
sleep(5)
closeBtn = browser.find_element_by_xpath("/html/body/div[5]/div[2]/a").click()  # close the first pop-up
sleep(1.5)
menu = browser.find_element_by_xpath("/html/body/div[3]/div[9]/a[1]").click()  # menu button-3 parallel lines
sleep(1.5)
login = browser.find_element_by_xpath("/html/body/div[5]/div[3]/div[1]/button[1]").click()  # login page
sleep(1.5)
mailBox = browser.find_element_by_xpath("/html/body/div[5]/div[4]/div[2]/div[1]/form/input[1]").send_keys(usrname)  # email box
sleep(0.7)
passwdBox = browser.find_element_by_xpath("/html/body/div[5]/div[4]/div[2]/div[1]/form/input[2]").send_keys(passwrd)  # passwd box
sleep(1)
loginButton = browser.find_element_by_xpath(
    "/html/body/div[5]/div[4]/div[2]/div[1]/form/button").click()  # log-in button
sleep(2)
pya.alert("I'm a robot, solve the captcha!", programTitle, "Done")
sleep(3)
dismiss = browser.find_element_by_xpath("/html/body/div[3]/div[8]/a[1]/div[3]/button[2]").click()
sleep(0.5)
closeChat = browser.find_element_by_xpath("/html/body/div[3]/div[8]/div[1]/a[2]").click()

sleep(2)
pya.moveTo(800, 800)
sleep(1)

# coordinates = browser.find_element_by_xpath("/html/body/div[3]/div[4]").text
# print(coordinates)
# coordinates = coordinates.strip()
# coorX = coordinates.split(',')[0]
# coorX = int(coorX)
# coorY = coordinates.split(',')[1]
# coorY = int(coorY)

greenColor = browser.find_element_by_xpath("/html/body/div[3]/div[13]/a[7]").click()
sleep(0.5)


def moveRight():  # avoid getting stuck between 3 blocks if the middle one is not reachable.
    luckyNum = random.randint(0, 1)
    if luckyNum == 0:
        pya.move(17, 0)
    elif luckyNum == 1:
        pya.move(24, 0)


def moveLeft():
    luckyNum = random.randint(0, 1)
    if luckyNum == 0:
        pya.move(-17, 0)
    elif luckyNum == 1:
        pya.move(-24, 0)


def moveUp():
    luckyNum = random.randint(0, 1)
    if luckyNum == 0:
        pya.move(0, -17)
    elif luckyNum == 1:
        pya.move(0, -24)


def moveDown():
    luckyNum = random.randint(0, 1)
    if luckyNum == 0:
        pya.move(0, 17)
    elif luckyNum == 1:
        pya.move(0, 24)


pya.alert("Scroll 8 times?", programTitle, "Yes")
sleep(1.5)
pya.moveTo(932, 574)
sleep(1)
for i in range(0, 8):
    pya.scroll(1)
    sleep(0.67)
sleep(1)


def draw(x, y):
    coordinatess = browser.find_element_by_xpath("/html/body/div[3]/div[4]").text
    print(coordinatess)
    coordinatess = coordinatess.strip()
    cX = coordinatess.split(',')[0]
    cX = int(cX)
    cY = coordinatess.split(',')[1]
    cY = int(cY)

    isXinPlace = False
    isYinPlace = False

    while cX != x:
        if cX < x:
            moveRight()
        elif cX > x:
            moveLeft()

        coordinatess = browser.find_element_by_xpath("/html/body/div[3]/div[4]").text
        print(coordinatess)
        coordinatess = coordinatess.strip()
        cX = coordinatess.split(',')[0]
        cX = int(cX)
        cY = coordinatess.split(',')[1]
        cY = int(cY)

    while cY != y:
        if cY > y:
            moveUp()
        elif cY < y:
            moveDown()

        coordinatess = browser.find_element_by_xpath("/html/body/div[3]/div[4]").text
        print(coordinatess)
        coordinatess = coordinatess.strip()
        cX = coordinatess.split(',')[0]
        cX = int(cX)
        cY = coordinatess.split(',')[1]
        cY = int(cY)
    pya.click()


pya.alert("Draw?", programTitle, "Yes")
sleep(5)

