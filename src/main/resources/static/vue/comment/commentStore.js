/*
	Pinia 
	1) 동작 순서
		- App 생성 (Vue 생성) = createApp()
		- Pinia 등록 = defineStore()
		- store 생성
			| = state : HTML 코드 자동으로 갱신하는 변수
			  = getters : 실제 계산 값
			  				=> 1,000, range...
			  = actions : 실제 서버와 연결 => state를 갱신
			-------------- store 생성
			사용자가 이벤트 (버튼 클릭, 마우스 클릭)
				|
			store => action 함수 호출
				|
			서버 연결 => axios/fetch
				|
			서버에서 요청 처리 결과값 읽기
				|
			store에 있는 => state변수를 변경
				|
			HTML에 적용
		vue 	vuex 	pinia(O) 			nust
		react	redux	tanstackQuery(O)	next(O)
		jsp		mvc		thymeleaf(O)		spring(O)
		=> javascript 
		=> java
		=> security / jwt / websocket (strom)
			
*/
const {defineStore} = Pinia
// defineStore => 새로운 store 생성
const useCommentStore=defineStore('comment',{
	// HTML 적용 => 전체 컴포넌트가 사용이 가능하게 변수 설정
	// state 변수는 자바의 static => 공통 사용 변수
	state:()=>({
		rList:[],
		curpage:1,
		totalpage:0,
		count:0,
		sessionId:'',
		fno:0,
		msg:'',
		upReplyNo:null,
		updateMsg:{},
		
	}),
	getters:{
		
	},
	actions:{
		async commentListData(fno){
			this.fno=fno
			// 서버연결
			const res=await api.get('/comment/list_vue',{
				params:{
					page:this.curpage,
					fno:fno
				}
			})
			console.log(res.data) // res.data => map
			this.rList=res.data.rList
			this.curpage=res.data.curpage
			this.totalpage=res.data.totalpage
			this.count=res.data.count
		},
		async commentInsert(msgRef) {
			if(this.msg=='') {
				msgRef?.focus()
				return 
			}
			const res=await api.post('/comment/insert_vue',{
				page:this.curpage,
				fno:this.fno,
				msg:this.msg
			})
			console.log(res.data) // res.data => map
			this.rList=res.data.rList
			this.curpage=res.data.curpage
			this.totalpage=res.data.totalpage
			this.count=res.data.count
			this.msg=''
		},
		// 수정
		toggleUpdate(no, msg) {
			this.upReplyNo=this.upReplyNo===no?null:no
			this.updateMsg[no]=msg
			
		},
		async commentUpdate(no) {
			const res=await api.put('/comment/update_vue',{
				no:no,
				fno:this.fno,
				page:this.curpage,
				msg:this.updateMsg[no]
			})
			console.log(res.data) // res.data => map
			this.rList=res.data.rList
			this.curpage=res.data.curpage
			this.totalpage=res.data.totalpage
			this.count=res.data.count
			this.upReplyNo=null
		},
		async commentDelete(no){
			const res=await api.delete('/comment/delete_vue',{
				params:{
					page:this.curpage,
					fno:this.fno,
					no:no
				}
			})
			console.log(res.data) // res.data => map
			this.rList=res.data.rList
			this.curpage=res.data.curpage
			this.totalpage=res.data.totalpage
			this.count=res.data.count
		},
		move(page){
			this.curpage=page
			this.commentListData(this.fno)
		}
	}
})