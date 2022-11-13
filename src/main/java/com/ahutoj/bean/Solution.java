package com.ahutoj.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class Solution implements Serializable
{
    private int SLTID;

    private String UID;

    private int PID;

    private String Content;

    private int ThumbsUp;

    private int ThumbsDown;

    private long UpdateTime;

    private int CommentCount;
}
