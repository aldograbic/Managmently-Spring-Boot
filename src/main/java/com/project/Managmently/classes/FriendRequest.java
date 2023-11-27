package com.project.Managmently.classes;

public class FriendRequest {
    private int id;
    private int senderId;
    private int receiverId;
    private boolean accepted;
    private User sender;
    
    public FriendRequest() {}

    public FriendRequest(int senderId, int receiverId, boolean accepted) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.accepted = accepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
