const app = Vue.createApp({
    data() {
        return {
            isCommentEmpty: true,
            isReplyEmtpy: true,
            isClickInput: false,
            userId: '',
            videoId: '',
            batchId: '',
            commentText: '',
            commentList: [],
            replyText: '',
        }
    },
    methods: {
        convertDateTime(timeMiliSecond) {
            if (timeMiliSecond <= 60000) {
                return commentedTime = 'JUST NOW';
            } else if (timeMiliSecond > 60000 && timeMiliSecond < 3600000) {
                let minute = Math.round((timeMiliSecond / 1000) / 60);
                return commentedTime = minute + ' minutes ago';
            } else if (timeMiliSecond > 3600000 && timeMiliSecond < 86400000) {
                let hour = Math.round(((timeMiliSecond / (1000 * 60 * 60)) % 24));
                return commentedTime = hour <= 1 ? `${hour} hour ago` : `${hour} hours ago`;
            } else if (timeMiliSecond > 86400000 && timeMiliSecond < 2629800000) {
                let day = Math.round(timeMiliSecond / 86400000);
                return commentedTime = day <= 1 ? `${day} day ago` : `${day} days ago`;
            } else if (timeMiliSecond > 2629800000 && timeMiliSecond < 31556952000) {
                let month = Math.round(timeMiliSecond / 2629746000);
                return commentedTime = month <= 1 ? `${month} month ago` : `${month} months ago`;
            } else if (timeMiliSecond > 31556952000) {
                let year = Math.round(timeMiliSecond / 31556952000);
                return commentedTime = year <= 1 ? `${year} year ago` : `${year} years ago`;
            }
        },
        clickInput() {
            this.isClickInput = true;
        },
        handleCancelInput() {
            this.isClickInput = false;
        },
        sendComment() {
            this.isCommentEmpty = this.commentText.replace(/\s/g, "").length <= 0 && true;
            if (!this.isCommentEmpty) {
                let data = {
                    userId: this.userId,
                    videoId: this.videoId,
                    batchId: this.batchId,
                    commentText: this.commentText,
                    dateTime: new Date().toLocaleString()
                }
                axios.post(`http://localhost:8080/api/comment/sendComment`, data)
                    .then(() => {
                        console.log('success')
                        this.getMessageList();
                    })
                    .catch(error => console.log(error));
                this.commentText = '';
            }
        },
        sendReply(commentId, index) {
            this.isReplyEmtpy = this.commentList[index].replyText.replace(/\s/g, "").length <= 0 && true;
            if (!this.isReplyEmtpy) {
                let data = {
                    replyText: this.commentList[index].replyText,
                    replyUserId: this.userId,
                    replyCommentId: commentId,
                    dateTime: new Date().toLocaleString()
                }
                axios.post(`http://localhost:8080/api/comment/sendReply`, data)
                    .then(() => {
                        this.getMessageList(index);
                        console.log('success')
                    })
                    .catch(error => console.log(error));
                this.commentList[index].replyText = '';
            }
        },
        toggleShowReply(index) {
            this.commentList[index].isReplyShow = !this.commentList[index].isReplyShow;
        },
        handleCancel(index) {
            this.commentList[index].isReplyShow = false;
        },
        getMessageList(index) {
            axios.get(`http://localhost:8080/api/comment/getCommentListByBatchIdAndVideoId/${this.batchId}/${this.videoId}`)
                .then((res = []) => {
                    this.commentList.length = 0;
                    this.commentList = res.data.map(cmt => {
                        return { ...cmt, replyText: '' }
                    });
                    this.commentList = this.commentList.map(cmt => {
                        cmt.replyList = cmt.replyList.map(reply => {
                            let currentTime = new Date();
                            let date = new Date(reply.dateTime);
                            let timeMiliSecond = currentTime - date;
                            let commentedTime = this.convertDateTime(timeMiliSecond);
                            return { ...reply, dateTime: commentedTime };
                        });
                        let currentTime = new Date();
                        let date = new Date(cmt.dateTime);
                        let timeMiliSecond = currentTime - date;
                        let commentedTime = this.convertDateTime(timeMiliSecond);
                        return { ...cmt, dateTime: commentedTime };
                    });
                    if (index >= 0) {
                        this.commentList[index].isReplyShow = true;
                    }
                })
                .catch(error => console.log(error));
        },
        handleChange(newVal) {
            // Handle changes here!
            console.log(newVal);
        },
    },
    mounted() {
        this.userId = document.getElementById('userId').value;
        this.videoId = document.getElementById('videoId').value;
        this.batchId = document.getElementById('batchId').value;

        if (this.videoId && this.batchId) {
            this.getMessageList();
        }
    }
})
app.mount('#app');