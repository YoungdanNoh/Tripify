import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { selectSiGunType } from "@/api/place";

export const useCustomerStore = defineStore("place", () => {
  //1. data
  const sigun = ref([]); //시, 군 정보
  const contentType = ref([]); //관광지 타입 정보

  //2. getters
  const getSigun = computed(() => sigun.value);
  const getContentType = computed(() => contentType.value);

  //3. actions
  const selectSigunType = () => {
    selectSiGunType(
      ({ data }) => {
        sigun.value = data.sigun;
        contentType.value = data.contentType;
      },
      (error) => {
        console.error(error);
      }
    ); //비동기 요청
  };

  /* 
  const allCustomers = () => {
    selectAll(
      ({ data }) => {
        customers.value = data;
      },
      (error) => {
        console.error(error);
      }
    ); //비동기 요청
  };

  const oneCustomer = (num) => {
    selectOne(num, ({ data }) => {
      customer.value = data;
    });
  };

  const insertCustomer = (customer) => {
    insert(
      customer,
      () => {
        alert("등록 되었습니다!");
      },
      () => {
        alert("등록 실패...");
      }
    );
  };

  const modifyCustomer = (c) => {
    modify(c, ({ data }) => {
      customer.value = c;
      alert(data.msg);
    });
  };

  const removeCustomer = (num) => {
    remove(num, ({ data }) => {
      if (data && data.msg) {
        alert(data.msg);
      } else {
        alert("삭제가 정상적으로 완료되지 않았습니다.");
      }
    });
  };*/

  return {
    sigun,
    contentType,
    getSigun,
    getContentType,
    selectSigunType,
    // modifyCustomer,
    // removeCustomer,
    // allCustomers,
    // oneCustomer,
    // insertCustomer,
  };
});
