Echos App Design
===

# Echos

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app that allows users to record themselves saying phrases in their language, and then also provide a translation.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Education/ Language Learning
- **Mobile:** Android
- **Story:** Users with unique dialects can help save their language by uploading audio clips sharing simple phrases. 
- **Market:** *do research*
- **Habit:**
- **Scope:**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] Login/Registration Page
- [x] Feed using RecyclerView
    - [x] Users can view posts

- [x] Compose
    - [ ] User can compose a post
    - [x] Each post has an audio clip (30sec max), translation, language, cave (category/topic), and user name
    - [ ] User can delete their own post
- [x] Bottom Navigation
    - [x] Contains home feed, personal feed, composition, and saved posts

**Optional Nice-to-have Stories**

- [ ] Users can save posts
- [ ] Users can click on a post to view more detail
- [ ] Users can follow other users
- [ ] Users can comment on posts
- [ ] If translation is too long, add 'Read More' to view the whole translation
- [ ] Users can search for different users/caves/languages
- [ ] Optional Flashcards 

### 2. Screen Archetypes

* Login/Registration Screen
   * User can log in
   * User can sign up
* Community Feed (RecyclerView)
    * User can view all posts
* Personal Feed (Profile)
    * User can view only their own posts 
    * Includes count of uploaded posts
* Compose
   * User can create a post, which includes recording an audio file and including a translation
* Saved Posts (optional)
    * Users can view their saved posts
    * Includes count of saved posts

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Search for users (optional) 
* Compose (might be moved to home screen)
* Home Screen (Feed)
* Profile
* Saved Posts (optional)

**Flow Navigation** (Screen to Screen)

* Login/Sign Up
   * Home Feed
* Home Screen (Feed) 
   * Compose

## Wireframes
<img src="https://i.imgur.com/c60KTXL.png" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="https://i.imgur.com/x7pytJK.png" width=800>

### [BONUS] Interactive Prototype

## Schema 
### Models
#### User
| Property | Type | Description |
| ---- | ---- | ----------- |
| objectId | String | unique id for the user (default field)|
| username | String | name of the user |
| password | String | password for loggin in |
| createdAt | DateTime | date when user was created |

#### Post
| Property | Type | Description |
| ---- | ---- | ----------- |
| objectId | String | unique id for the user post (default field)|
| username | Pointer to User | author of post |
| recording | File | audio file of recording |
| language | String | language of the recording |
| translation | String | translation of the recording |
| category | String | category of recording |
| transliteration | String | transliteration of recording |
| createdAt | DateTime | date when post was created (default field) |

### Networking
#### List of network requests by screen
- Login/Registration Screen
    - (Read/GET) Query in user object
    - (Create/USER) Create a new user
- Home/Community Feed Screen
    - (Read/GET) Query all posts
- Create Post Screen
    - (Create/POST) Create a new post object
- Profile/Personal Feed Screen
    - (Read/GET) Query logged in user object
    - (Read/GET) Query all posts where user is the author

**[Create basic snippets for each Parse network request]**
**[OPTIONAL: List endpoints if using existing API such as Yelp]**
