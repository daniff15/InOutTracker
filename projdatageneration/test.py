import random
import requests
from simulate import *
from random import randint, choices, choice
import time
from copy import deepcopy
import json
import pulsar
import sys

store1_1 = Store(1 ,"Zara", 5, "10:00:00", "23:00:00")
store2_1 = Store(2 ,"Sport Zone", 5, "10:00:00", "22:00:00")
store3_1 = Store(3 ,"Primark", 5, "10:00:00", "23:00:00")
store4_1 = Store(4 ,"Modalfa", 5, "10:00:00", "22:00:00")
store1_2 = Store(5 ,"Zara", 50, "10:00:00", "22:00:00")
store2_2 = Store(6 ,"Aucham", 100, "10:00:00", "22:00:00")
store3_2 = Store(7 ,"MEO", 5, "10:00:00", "23:00:00")
store4_2 = Store(8 ,"Breshka", 35, "10:00:00", "23:00:00")
store1_3 = Store(9 ,"Intimissimi", 35, "10:00:00", "21:00:00")
store2_3 = Store(10 ,"Pull&Bear", 45, "10:00:00", "22:00:00")

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

mall1 = Mall(1, "Mall 1", 10, stores1, "9:00:00", "23:00:00")
mall2 = Mall(2, "Mall 2", 350, stores2 , "10:00:00", "23:00:00")

malls = [mall1, mall2]

if __name__ == '__main__':
    #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
    pop = [1, 2, 3, 4]
    weights = [0.30, 0.23, 0.23, 0.23]

    #1-respeitou 2-nao respeitou
    pop_waitLine = [1, 2]
    weights_waitLine = [0.90, 0.10]

    #! EM ALGUNS SITIOS (MAYBE?) FALTAM METER CONDICOES PARA VER SE RESPEITOU A FILA
    while True:
        #Choose the mall (random)
        mall_idx = randint(0, len(malls) - 1)
        mall = malls[mall_idx]

        # primeira imp, apenas um mall e simulação em varias lojas
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
        idx_for_store = randint(0, len(mall.stores)-1)
        store = mall.stores[idx_for_store]

        if choices(pop, weights)[0] == 1:
            # e condicao para verificar se respeitou o limite
            if len(mall.inside_mall_ids) < mall.mall_limit:
                if len(mall.waiting_mall_ids) > 0:
                    mall.waiting_list_to_inside_mall()

                    if len(store.inside_store_ids) >= store.store_limit: #and respeitou:
                        store.waiting_outside_Store(mall.people_id)
                    else:
                        store.enterStore(mall.people_id) 
                    mall.people_id += 1

                else:
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

        elif choices(pop, weights)[0] == 2:
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
                    mall.waiting_list_to_inside_mall()

                    if len(store.inside_store_ids) >= store.store_limit: #and respeitou:
                        store.waiting_outside_Store(mall.people_id)
                    else:
                        store.enterStore(mall.people_id) 
                    mall.people_id += 1

        elif choices(pop, weights)[0] == 3:
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
                print("DEVIAS RESPEITAR A FILA!")
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

        else:
            #e condicao para verificar se se cansou de esperar na fila
            if len(mall.inside_mall_ids) >= mall.mall_limit and len(mall.waiting_mall_ids) > 0:
                idx_temp = randint(0 , len(mall.waiting_mall_ids) - 1)
                mall.stop_waiting_outside_Mall(idx_temp)

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

        print("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|")
        """#escolha da loja para fazer cenas randoms
        #list_idx = randint(0, len(mall.stores))
        print(store.store_name)
        #print("Store - ", (mall_1.stores)[1])
        exit(0)"""

        
        
        time.sleep(0.1)