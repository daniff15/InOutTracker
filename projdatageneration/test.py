from numpy import ma
from simulate import *
from random import randint, choices, choice
import time
from copy import deepcopy
import datetime

store1_1 = Store(1 ,"Zara", 10, "10h00", "23h00")
store2_1 = Store(2 ,"Sport Zone", 10, "10h00", "22h00")
store3_1 = Store(3 ,"Primark", 10, "10h00", "23h00")
store4_1 = Store(4 ,"Modalfa", 10, "10h00", "22h00")
store1_2 = Store(5 ,"Zara", 5, "10h00", "22h00")
store2_2 = Store(6 ,"Aucham", 5, "10h00", "22h00")
store3_2 = Store(7 ,"MEO", 5, "10h00", "23h00")
store4_2 = Store(8 ,"Breshka", 5, "10h00", "23h00")
store1_3 = Store(9 ,"Intimissimi", 35, "10h00", "21h00")
store2_3 = Store(10 ,"Pull&Bear", 45, "10h00", "22h00")

stores1 = []
stores1.append(store1_1)
stores1.append(store2_1)
stores1.append(store3_1)
stores1.append(store4_1)

stores2 = []
stores2.append(store1_2)
stores2.append(store2_2)
stores2.append(store3_2)
stores2.append(store4_2)

mall1 = Mall(1, "Mall 1", 5, stores1, "09h00", "15h00")
mall2 = Mall(2, "Mall 2", 350, stores2 , "10h00", "23h00")

malls = [mall1, mall2]
#malls = [mall1]

if __name__ == '__main__':
    #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
    pop = [1, 2, 3, 4]
    weights = [0.30, 0.23, 0.23, 0.23]

    #1-respeitou 2-nao respeitou
    pop_waitLine = [1, 2]
    weights_waitLine = [0.90, 0.10]

    #Starting day
    hours_of_day = [00 , 00]
    #format 00 - h - 00
    #hours_of_day = ["00" , "00"]
    #               horas, minutos
    day = 0
    #In case the store or mall are closed, do nothing
    numbers_of_iters = 0

    while True:
        #Choose the mall (random)

        mall_idx = randint(0, len(malls) - 1)
        mall = malls[mall_idx]

        if hours_of_day == [00 , 00] and numbers_of_iters == 0:
            print("NEW DAY")
            day += 1
            print("DAY - ", day)

        # primeira imp, apenas um mall e simulação em varias lojas
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
        idx_for_store = randint(0, len(mall.stores)-1)
        store = mall.stores[idx_for_store]

        if len(store.inside_store_ids) >= store.store_limit:
            #print("ENTREI PARA " , mall.mall_name)
            weights = [0.25, 0.40, 0.20, 0.15]
        else:
            weights = [0.30, 0.23, 0.23, 0.23]

        val = choices(pop, weights)[0]

        #mall or store are closed
        #if mall is closed than all stores are closed, so check first mall
        opening_mall = mall.opening_time.split("h")
        for i in range(len(opening_mall)):
            opening_mall[i] = int(opening_mall[i])

        closing_mall = mall.close_time.split("h")
        for i in range(len(closing_mall)):
            closing_mall[i] = int(closing_mall[i])

        if (opening_mall[0] > hours_of_day[0]): #shopping fechado
            val = 5
        elif (opening_mall[0] == hours_of_day[0] and opening_mall[1] > hours_of_day[1]): #shopping fechado
            val = 5
        elif (closing_mall[0] < hours_of_day[0]):
            val = 5
        elif (closing_mall[0] == hours_of_day[0] and closing_mall[1] <= hours_of_day[1]):
            val = 5


        if val == 5:
            mall.inside_mall_ids = []
            mall.waiting_mall_ids = []
            for store in mall.stores:
                if len(store.inside_store_ids) > 0:
                    store.inside_store_ids = []
                if len(store.waiting_store_ids) > 0:
                    store.waiting_store_ids = []
            for shopping in malls:
                if mall.close_time == shopping.close_time:
                    shopping.inside_mall_ids = []
                    shopping.waiting_mall_ids = []
                    for store in shopping.stores:
                        if len(store.inside_store_ids) > 0:
                            store.inside_store_ids = []
                        if len(store.waiting_store_ids) > 0:
                            store.waiting_store_ids = []
        #print("1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall")
        #print("VAL - ", val)
        #print("WEIGHTS - ", weights)
        #print("LOJA ESCOLHIDA - ", store.store_name)

        if val == 1:
            # e condicao para verificar se respeitou o limite
            if len(mall.inside_mall_ids) < mall.mall_limit:
                mall.enterMall(mall.people_id)
                #Condicao que verifica se a pessoa respeitou a fila de espera
                respeitou_int = choices(pop_waitLine, weights_waitLine)[0]
                if respeitou_int == 1:
                    respeitou = True
                else:
                    respeitou = False
                if len(store.inside_store_ids) >= store.store_limit and respeitou:
                    store.waiting_outside_Store(mall.people_id)
                else:
                    #!Supostamente ja entra quando nao respeita
                    store.enterStore(mall.people_id)
                mall.people_id += 1

        elif val == 2:
            if len(mall.inside_mall_ids) > 0:
                idx = randint(0 , len(mall.inside_mall_ids) - 1)
                person_id = mall.inside_mall_ids[idx]
                mall.exitMall(idx)
                #remove person from store if its in one
                for loja in mall.stores:
                    if person_id in loja.inside_store_ids:
                        idx_to_remove = loja.inside_store_ids.index(person_id)
                        loja.exitStore(idx_to_remove)
                        if len(loja.waiting_store_ids) > 0:
                            loja.waiting_list_to_inside_store()
                #remove person from waiting list of store if the person leaves the mall 
                for loja in mall.stores:
                    if person_id in loja.waiting_store_ids:
                        idx_to_remove = loja.waiting_store_ids.index(person_id)
                        loja.stop_waiting_store(idx_to_remove)
                if len(mall.waiting_mall_ids) > 0:
                    val = mall.waiting_list_to_inside_mall()

                    if len(store.inside_store_ids) >= store.store_limit: #and respeitou:
                        store.waiting_outside_Store(val)
                    else:
                        store.enterStore(val)
                        mall.people_id += 1 

        elif val == 3:
            # e condicao para verificar se respeitou o limite
            respeitou_int = choices(pop_waitLine, weights_waitLine)[0]

            if respeitou_int == 1:
                respeitou = True
            else:
                respeitou = False

            if len(mall.inside_mall_ids) >= mall.mall_limit and respeitou:
                mall.waiting_outside_Mall(mall.people_id)
                mall.people_id += 1
            elif len(mall.inside_mall_ids) >= mall.mall_limit and not respeitou:
                mall.enterMall(mall.people_id)

                #nao respeitou e agora tem de entrar numa loja
                respeitou_int = choices(pop_waitLine, weights_waitLine)[0]

                if respeitou_int == 1:
                    respeitou = True
                else:
                    respeitou = False

                if len(store.inside_store_ids) >= store.store_limit and respeitou:
                    store.waiting_outside_Store(mall.people_id)
                else:
                    #!Supostamente ja entra quando nao respeita
                    store.enterStore(mall.people_id)
                mall.people_id += 1

        elif val == 4:
            #e condicao para verificar se se cansou de esperar na fila
            if len(mall.inside_mall_ids) >= mall.mall_limit and len(mall.waiting_mall_ids) > 0:
                idx_temp = randint(0 , len(mall.waiting_mall_ids) - 1)
                mall.stop_waiting_outside_Mall(idx_temp)
            
        else: #val == 5 #store or mall are closed
            #just pass the time
            pass
            
        #To see when to increase the hours_of_day    
        numbers_of_iters += 1

        ''' implementation for one mall '''
        stores_capacity = {mall.id: len(mall.inside_mall_ids)}
        for store in mall.stores:
            stores_capacity[store.id] = len(store.inside_store_ids)

        for shopping in malls:
            print("--------------START--------------")
            print("SHOPPING ESCOLHIDO - ", shopping.mall_name)
            print("Inside mall - ", shopping.inside_mall_ids)
            print("Waiting mall - ", shopping.waiting_mall_ids)
            print("LEN - ", len(shopping.inside_mall_ids))
            for store in shopping.stores:
                print("INSIDE  ", store.store_name , " - " , store.inside_store_ids)           
                print("WAITING ", store.store_name, " - ", store.waiting_store_ids)
                print("LEN - ", len(store.inside_store_ids))
            print("--------------END--------------")

        #JUST TO TEST INCREASE ON HOURS_OF_DAY
        if numbers_of_iters == 5:
            numbers_of_iters = 0
            if hours_of_day == [23 , 45]:
                hours_of_day = [00 , 00]
            elif hours_of_day[1] != 45:
                hours_of_day[1] += 15
            elif hours_of_day[1] == 45:
                hours_of_day[0] += 1
                hours_of_day[1] = 00

        print("HOURS OF DAY  - " , hours_of_day)
        print("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|")
        
        time.sleep(0.025)