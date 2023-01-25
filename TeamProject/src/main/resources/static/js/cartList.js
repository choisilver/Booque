/**
 * 
 */
 
  window.addEventListener('DOMContentLoaded', () => {
   readCartDesc();
   

    
  

    

    
   function readCartDesc(){
        const userid = document.querySelector('#userId').value;
        
        axios.get('api/cart/all/' + userid) 
        .then(response => { updateCartList(response.data)
        
        })
        .catch(err => {console.log(err) });
        
        
    };
    
    function updateCartList(data){
        const divCart = document.querySelector('#cList')
        const username = document.querySelector('#un').value;
        let str = '';
        
        str += ' <form id="formCheck" mehtod="post">' 
        +'<table class="w-100 table" style="text-align: center;"> '
        + '<thead class="table-light"> '
         +  '  <tr> '
         +'<th> <input type="checkbox"  id="chkBoxAll"  style="width: 30px;" checked> </th>'
          +      ' <th colspan="2">도서 정보</th> '
           +   '   <th>수량</th> '
            +  '   <th>배송비</th> '
            +   '  <th>배송정보</th> '
            +  '  <th>선택</th> '
            +  '  <th></th> '
          + '  </tr> '
      + '  </thead> '
     + '   <tbody style="height: 200px;"> ';
        
        
        for(let c of data){
        
      str  += '<tr>'
            +  '<td class="align-middle">' 
            +   ' <input type="checkbox" checked  id="ckBox" style="width: 30px;"  name="cartId" value="'+ c.cartId +'"/>' 
            +   `<a href="/detail?id=${c.bookId}" onclick="viewHitUp(${c.bookId}, ${username});"><img src="${c.image}" style="width: 150px;"/></a>  </td>` 
            +   ' <td class="align-middle" style="text-align: left;">' 
            +              '  <small class="d-inline-flex px-2 my-1 border rounded text-secondary">' 
            +                 '   <span>'+c.group+'</span><span> / </span><span>'+c.category+'</span>' 
            +              '  </small>' 
            +              '  <div class="h5">'+c.title+'</div>' 
            +             '   <div >'+c.author+'</div>' 
            +              '  <div >'
            +                '    <span class="onePrice"> '+c.prices+'</span>' 
            +                 '   <small class="px-1 border border-primary rounded text-primary">p</small>' 
            +                  '  <small class="text-primary">'+c.prices*0.05+'</small> ' 
            +               ' </div>' 
            +  '  </td>' 
            +  ' <td>'
            +  '  </td>' 

            +  '  <td class="align-middle">' 
            +  '  <div class="selectPrice " >' 
            +        '<span class="prices"  >'+c.prices*c.count +'</span>' + '<span>원</span>'
            +  '<input type="hidden" id="fixedPrice" value="'+c.prices+'"/>' 
            +   ' </div>' 
            +       ' <input type="button" class="btnPlusMinus btn btn-outline-secondary  my-2"  value="-"/>'
            +     '   <span style="width: 50px" class="count btn" >'+c.count +' </span>' 
            +       ' <input type="button" class="btnPlusMinus btn btn-outline-secondary  my-2"  value="+"/>'
            +  '  <div class="selectPrice">' 
            +      '  <div id="price" >' 
            +     '   </div>' 
            +   ' </div>' 
            +  '  </td>' 
            +  '  <td class="align-middle">30,000원 이상<br/>' 
            +   '     배송비 무료</td>' 
            +  '  <td class="align-middle">' 
            +      '  <button type="button" class="btn btn-dark btn-sm my-2" style="width: 100px;">Buy Now</button><br/>' 
            +     '   <button type="button" id="btnOneDelete" class="btn btn-danger btn-sm my-2" style="width: 100px;" value="'+c.cartId+'"  >Delete</button>' 
            +  '  </td>'
            + '</tr>'; 
        }
        
        
        
        
       str 
            += '</tbody>'
            + '</table>'
            +   ' </form>' ;
            
        divCart.innerHTML = str;
        totalFun();
        
        let oneP = document.querySelectorAll('.onePrice')
        
        oneP.forEach(p => {
            let oP = p.innerText
            p.innerText = oP.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
        })
        
        let onePC = document.querySelectorAll('.prices')
        
        onePC.forEach(p => {
            let oP = p.innerText
            p.innerText = parseInt(oP).toLocaleString('ko-KR');
        })
        

        

        
        
        
        
        const buttons = document.querySelectorAll('.btnPlusMinus');
        buttons.forEach(btn => {
            
            btn.addEventListener('click', e => {
                const td = btn.closest('tr');
                const span = td.querySelector('span.count');
                const pri = td.querySelector('span.prices'); // 변경될 값
                const fix = td.querySelector('input#fixedPrice').value;
                const cartId = td.querySelector('#ckBox').value;
                
        
                let number = span.innerText;
                
                const type = btn.value;
                if (type == '+') {
                    number = parseInt(number) + 1;
                } else {
                    number = parseInt(number) - 1;
                    
                    if(number == 0){
                       alert('수량은 0이하가 되지 못합니다.')
                       pri.innerText = fix;
                       return;
                   } 
                    
                }
                span.innerText = number;
                pri.innerText= (number * parseInt(fix)).toLocaleString()
                count =number;
                totalFun();
                
                // ajax로 장바구니 값 변경할것임. number값

                axios.get('/api/cartCount/'+count+'/' +cartId)
                .catch(err => {console.log(err)})

                 
                
                
            })
                
                
            }); // forEach end

    
        const form = document.querySelector('#formCheck')
            // 결제창으로 넘어감.
        const btnOrder = document.querySelector('#btnOrder')
        btnOrder.addEventListener('click', function() {
            form.action = '/order';
            form.method = 'post';
            form.submit();

            console.log('누름')
         
        });
    
    
    
    // check box
    const list = document.querySelectorAll('#ckBox');
    list.forEach(e => {
        e.addEventListener('change', function() {
            totalFun();
        })
        
    })
    
    
        
    // 함수를 만들어서 전체 가격을 가져오면 됨.
    // 금액 값을 읽어서 우선 기록을 해.
    function totalFun(){
        // 체크된 금액만 가져와
        // total 에 넣기 
        const totalPrice = document.querySelector('#totalPrice');
        const delivery = document.querySelector('#delivery')
        const totalPoint = document.querySelector('#totalPoint');
        const total = document.querySelector('#total')
        const chkAllList = document.querySelectorAll('#ckBox');
        let sum = 0;
        
        chkAllList.forEach(chk =>{
        
            if(chk.checked){
                const tr = chk.closest('tr');
                const priS = tr.querySelector('span.prices').innerText; // 변경될 값
                const priInt = parseInt(priS.replace(/,/g,''))
                console.log(priS)
                console.log(priInt)
                sum += priInt
            }
            
        })
        
        totalPoint.innerText = sum*0.05;
        total.innerText = sum.toLocaleString('ko-KR')
        if(sum>=30000){
            delivery.innerText='3만원 이상 구매, 무료 배송입니다.'
            totalPrice.innerText = total.innerText;
        }else {
            delivery.innerText = '3,000원'
            totalPrice.innerText =(sum+3000).toLocaleString();
        }
        
    }    
        
    
    
    
    const chkBoxAll = document.querySelector('#chkBoxAll')
    chkBoxAll.addEventListener('change', function(){
        
        const chkBox = document.querySelectorAll('#ckBox');
            chkBox.forEach((check) => {
                check.checked = chkBoxAll.checked
                totalFun();
            })
    
    })
    
    
    // 삭제 
    const btnOneDelete = document.querySelectorAll('#btnOneDelete')
    const userId = document.querySelector('#userId').value;
    btnOneDelete.forEach(btn => {
        
        btn.addEventListener('click',function(){
            console.log('카트 번호'+btn.value);
            let ckList = [];
        
            
            ckList.push(btn.value)
            ckList.push(userId)
            
            deleteButton(ckList);
            
        })
    })
    
    
    
    
    
    } // function updatCartList end --
    
    

            
            

    
    
    
    
    // 선택 삭제
    const btnDelete = document.querySelector('#btnDelete')
    btnDelete.addEventListener('click', function(){
    const userId = document.querySelector('#userId').value;
    const list = document.querySelectorAll('#ckBox');
    let ckList = [];
    
        
        for(let i=0; i<list.length; i++ ){
            if(list[i].checked){
                let a = list[i].value;
                ckList.push(a);
            }
            
        }
        ckList.push(userId)
        deleteButton(ckList);
        
        }) 
        
        
    function deleteButton(list){
            const result = confirm('선택한 책을 정말 삭제하시겠습니까?')
            
            if(result){
                axios
                .post('api/cartid', list)
                .then(response => {
                    updateCartList(response.data)
                    console.log(response.data);
                })
                .catch(err => {console.log(err)})
            }
    }
        

    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
 })







