const app = Vue.createApp({
    data() {
        return {
            userId: '',
            videoId: '',
            batchId: '',
            commentText: '',
            commentList: [],
            replyText: '',
        }
    },
    methods: {
        sendComment() {
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
        },
        sendReply(commentId, index) {
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
        },
        toggleShowReply(index) {
            this.commentList[index].isReplyShow = !this.commentList[index].isReplyShow;
        },
        getMessageList(index) {
            axios.get(`http://localhost:8080/api/comment/getCommentListByBatchIdAndVideoId/${this.batchId}/${this.videoId}`)
                .then(res => {
                    this.commentList.length = 0;
                    let resCommentList = [...res.data];
                    resCommentList.forEach(obj => {
                        obj = { ...obj, replyText: '' };
                        this.commentList.push(obj);
                    });
                    if (index >= 0) {
                        this.commentList[index].isReplyShow = true;
                        console.log(this.commentList[index].isReplyShow);
                    }
                })
                .catch(error => console.log(error));
        }
    },
    watch: {
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