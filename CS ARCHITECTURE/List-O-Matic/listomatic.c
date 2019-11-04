//This code was written by Zach Dworkin
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct dll_node {//this is the object for creating linked list nodes
  int value;
  struct dll_node *prev;
  struct dll_node *next;
} dll_node_t;
//these are the dummy heads and tails at the start and end of the linked lists
struct dll_node dummy_head;
struct dll_node dummy_tail;
struct dll_node *head = &dummy_head;
struct dll_node *tail = &dummy_tail;
//these are the dummy heads and tails at the start and end of the LIFO linked list
struct dll_node dummy_head2;
struct dll_node dummy_tail2;
struct dll_node *head2 = &dummy_head2;
struct dll_node *tail2 = &dummy_tail2;

int read_list(int count){
  int val;
  while(scanf("%d", &val)==1){//only accepts number inputs
    count++;
    struct dll_node *new_node;
    new_node = malloc(sizeof(struct dll_node));//adds a new node while appropriating memory space for it
    struct dll_node *new_node2;
    new_node2 = malloc(sizeof(struct dll_node));
    if(new_node == NULL){//if the value that we are trying to input is NULL, stop the program bc it broke
      printf("oops");
      exit(1);
      }
    //linked list 1
    new_node-> value=val;
    new_node-> next=tail;
    new_node-> prev=tail->prev;
    tail->prev->next=new_node;
    tail->prev=new_node;

    //linked list 2 (the LIFO list)
    new_node2->value=val;
    new_node2->prev=head2;
    new_node2->next=head2->next;
    head2->next->prev=new_node2;
    head2->next=new_node2;
    
  }
  return count;//returns the number of items in the lists
}

void swap(struct dll_node *left, struct dll_node *right){
  //swaps the nodes by moving the pointers around
  left->prev->next=right;
  right->next->prev=left;
  left->next=right->next;
  right->prev=right->prev->prev;
  left->prev=right;
  right->next=left;
}

bool sort_list_asc(){
  struct dll_node *start=head->next;//starting node
  bool flag=false;//flag to see if we changed the list
  int swapped=1;//flag to see if we swapped anything
  while(swapped==1){
    swapped=0;
    while(start->next!=NULL && start->next->next!=NULL){
      if(start->value > start->next->value){
	swap(start,start->next);//swaps the node and the node to the right of it (we know it is not swapping the tail node b/c it won't enter the loop if the tail node is the one to the next of the node we are at
	swapped=1;//makes it so this loop will keep looping until the end of the list
	flag=true;//makes it so that the loop in main will keep looping
      }
      start=start->next;//moves to the next node
    }
  }
  return flag;//returns whether or not we made a swap
}

bool sort_list_dsc(){
  struct dll_node *start=head->next;
  bool flag=false;
  int swapped=1;
  while(swapped==1){
    swapped=0;
    while(start->next!=NULL && start->next->next!=NULL){
      if(start->value < start->next->value){
	swap(start,start->next);//swaps the node and the node to the right of it (we know it is not swapping the tail node b/c it won't enter the loop if the tail node is the one to the next of the node we are at
	swapped=1;//makes it so this loop will keep looping until the end of the list
	flag=true;//makes it so that the loop in main will keep looping
      }
      start=start->next;//moves to the next node
    }
  }
  return flag;//returns whether or not we made a swap
}

void print_list(){
  struct dll_node *node;
  node=head->next;//starting node
  while(node != tail){//go until we hit the tail which is not part of our list
    printf("%d\n",node->value);//print the value of this node
    node=node->next;//go to the next node
  }
}

void print_LIFO(){
  struct dll_node *node;
  node=head2->next;//starting node
  while(node != tail2){//goes until we are at the end of the LIFO list
    printf("%d\n",node->value);//print the value of this node
    node=node->next;//go to the next node in the LIFO list
  }
}

int main(){
  //initialize the dummies
  int count=0;
  head->next=tail;
  head->prev=NULL;
  head->value=NULL;
  tail->value=NULL;
  tail->next=NULL;
  tail->prev=head;
  head2->next=tail2;
  head2->prev=NULL;
  head2->value=NULL;
  tail2->value=NULL;
  tail2->next=NULL;
  tail2->prev=head2;
  
  count=read_list(count);//collects a count of the number of inputs
  printf("Read %d integers\n", count);//prints the count of the number of inputs
  bool flag=true;
  if(count>1){//checks to see if our list has more than one or no items. If there was only one or no items in the list, it is already sorted
    while(flag==true){
      flag=sort_list_asc();
    }
  }
  print_list();//prints list in asc order
  flag=true;
  if(count>1){//checks to see if our list has more than one or no items. If there was only one or no items in the list, it is already sorted
    while(flag==true){
      flag=sort_list_dsc();
    }
  }
  print_list();//prints the list in dsc order
  print_LIFO();//prints the second list copy that we made
  return 0;//return statment at the end of main
}
