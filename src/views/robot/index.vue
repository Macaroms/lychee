<template>
  <page-header-wrapper :title='false'>
    <a-card :body-style="{padding: '24px 32px'}" :bordered='false'>
      <a-divider orientation='left'>人民币数字转大写</a-divider>
      <a-spin :spinning='confirmLoading'>
        <div>
          <beautiful-chat
            :participants="participants"
            :titleImageUrl="titleImageUrl"
            :onMessageWasSent="onMessageWasSent"
            :messageList="messageList"
            :newMessagesCount="newMessagesCount"
            :isOpen="isChatOpen"
            :close="closeChat"
            :open="openChat"
            :showEmoji="true"
            :showFile="true"
            :showEdition="true"
            :showDeletion="true"
            :showTypingIndicator="showTypingIndicator"
            :showLauncher="true"
            :showCloseButton="true"
            :colors="colors"
            :alwaysScrollToBottom="alwaysScrollToBottom"
            :disableUserListToggle="false"
            :messageStyling="messageStyling"
            @onType="handleOnType"
            @edit="editMessage" />
        </div>
      </a-spin>
    </a-card>
<!--    <a-card style='margin-top: 20px' :bordered='false'>-->
<!--      <a-divider orientation='left'>对照表</a-divider>-->
<!--      <a-table-->
<!--        :columns="columns"-->
<!--        :data-source="data"-->
<!--        rowKey="zero"-->
<!--        :pagination="false"-->
<!--        bordered-->
<!--      >-->
<!--        <template slot="name" slot-scope="text">-->
<!--          <a>{{ text }}</a>-->
<!--        </template>-->
<!--      </a-table>-->
<!--    </a-card>-->
  </page-header-wrapper>
</template>

<script>


export default {
  name: 'jsonEditor',
  components: {
  },
  data() {
    return {
      form: this.$form.createForm(this),
      confirmLoading: false,
      participants: [
        {
          id: 'user1',
          name: 'Matteo',
          imageUrl: 'https://avatars3.githubusercontent.com/u/1915989?s=230&v=4'
        },
        {
          id: 'user2',
          name: 'Support',
          imageUrl: 'https://avatars3.githubusercontent.com/u/37018832?s=200&v=4'
        }
      ], // 对话的所有参与者的列表。' name '是用户名，' id '用于建立消息的作者，' imageUrl '应该是用户头像。
      titleImageUrl: 'https://a.slack-edge.com/66f9/img/avatars-teams/ava_0001-34.png',
      messageList: [
        { type: 'text', author: `me`, data: { text: `Say yes!` } },
        { type: 'text', author: `user1`, data: { text: `No.` } }
      ], // // 要显示的消息列表可以动态地分页和调整
      newMessagesCount: 0,
      isChatOpen: false, // 确定聊天窗口应该打开还是关闭
      showTypingIndicator: '',  // 当设置为匹配参与者的值时。它显示特定用户的输入指示
      colors: {
        header: {
          bg: '#4e8cff',
          text: '#ffffff'
        },
        launcher: {
          bg: '#4e8cff'
        },
        messageList: {
          bg: '#ffffff'
        },
        sentMessage: {
          bg: '#4e8cff',
          text: '#ffffff'
        },
        receivedMessage: {
          bg: '#eaeaea',
          text: '#222222'
        },
        userInput: {
          bg: '#f4f7f9',
          text: '#565867'
        }
      }, // specifies the color scheme for the component
      alwaysScrollToBottom: false, // 当设置为true时，当有新事件发生时(新消息，用户开始输入…)，总是将聊天滚动到底部。
      messageStyling: true
    }
  },
  computed: {},
  created() {
  },
  mounted() {
  },
  methods: {
    sendMessage (text) {
      if (text.length > 0) {
        this.newMessagesCount = this.isChatOpen ? this.newMessagesCount : this.newMessagesCount + 1
        this.onMessageWasSent({ author: 'support', type: 'text', data: { text } })
      }
    },
    onMessageWasSent (message) {
      // 当用户发送消息时调用
      this.messageList = [ ...this.messageList, message ]
    },
    openChat () {
      // 当用户单击fab按钮打开聊天时调用
      this.isChatOpen = true
      this.newMessagesCount = 0
    },
    closeChat () {
      // // 当用户单击按钮关闭聊天时调用
      this.isChatOpen = false
    },
    handleScrollToTop () {

      // 当用户将消息列表滚动到顶部时调用
// 利用分页来加载另一个消息页面

    },
    handleOnType () {
      console.log('Emit typing event')
    },
    editMessage(message){
      const m = this.messageList.find(m=>m.id === message.id);
      m.isEdited = true;
      m.data.text = message.data.text;
    }
  }
}
</script>
