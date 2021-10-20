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

* Login/Registration Page
* Feed using RecyclerView
    * Users can view posts

* Compose
    * User can compose a post
    * Each post has an audio clip (30sec max), translation, language, cave (category/topic), and user name
    * User can delete their own post
* Bottom Navigation
    * Contains home feed, personal feed, composition, and saved posts

**Optional Nice-to-have Stories**

* Users can save posts
* Users can click on a post to view more detail
* Users can follow other users
* Users can comment on posts
* If translation is too long, add 'Read More' to view the whole translation
* Users can search for different users/caves/languages
* Optional Flashcards 

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
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
