import random
from simulate import *
from random import randint, choices
import time

store1_1 = Store("Zara", 10, "10:00:00", "23:00:00")
store2_1 = Store("Sport Zone", 55, "10:00:00", "22:00:00")
store3_1 = Store("Primark", 70, "10:00:00", "23:00:00")
store4_1 = Store("Modalfa", 35, "10:00:00", "22:00:00")
store1_2 = Store("Zara", 50, "10:00:00", "22:00:00")
store2_2 = Store("Aucham", 100, "10:00:00", "22:00:00")
store3_2 = Store("MEO", 5, "10:00:00", "23:00:00")
store4_2 = Store("Breshka", 35, "10:00:00", "23:00:00")
store1_3 = Store("Intimissimi", 35, "10:00:00", "21:00:00")
store2_3 = Store("Pull&Bear", 45, "10:00:00", "22:00:00")

mall1 = Mall("Mall 1", 50, [store1_1, store2_1,
             store3_1, store4_1], "9:00:00", "23:00:00")
mall2 = Mall("Mall 2", 350, [store1_2, store2_2,
             store3_2, store4_2], "10:00:00", "23:00:00")
mall3 = Mall("Mall 3", 250, [store1_3, store1_3], "9:30:00", "22:00:00")

if __name__ == '__main__':

    pop = [1, 2, 3, 4]
    weights = [0.30, 0.23, 0.23, 0.23]
    id = 0

    while True:
        #! Escolher um shopping e fazer o q tem de fazer
        # primeira imp, apenas um mall
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall

        if choices(pop, weights)[0] == 1:
            # e condicao para verificar se respeitou o limite
            if len(mall1.inside_mall_ids) < mall1.mall_limit:
                if len(mall1.waiting_mall_ids) > 0:
                    mall1.waiting_list_to_inside_mall()

                    #TODO: METER AS CONDICOES DO Q A PESSOA VAI FAZER QUANDO ENTRAR NO SHOPPING
                    if len(mall1.stores[0].inside_store_ids) >= mall1.stores[0].store_limit: #and RESPEITOU:
                        mall1.stores[0].waiting_outside_Store(id)
                    else:
                        mall1.stores[0].enterStore(id) 

                    id += 1

                else:
                    mall1.enterMall(id)

                    #TODO: METER AS CONDICOES DO Q A PESSOA VAI FAZER QUANDO ENTRAR NO SHOPPING
                    if len(mall1.stores[0].inside_store_ids) >= mall1.stores[0].store_limit: #and RESPEITOU:
                        mall1.stores[0].waiting_outside_Store(id)
                    else:
                        mall1.stores[0].enterStore(id)

                    id += 1

        elif choices(pop, weights)[0] == 2:
            if len(mall1.inside_mall_ids) > 0:
                idx = randint(0 , len(mall1.inside_mall_ids) - 1)
                print("idx - " , idx)
                person_id = mall1.inside_mall_ids[idx]
                mall1.exitMall(idx)

                #remove person from store if its in one
                if person_id in mall1.stores[0].inside_store_ids:
                    idx_to_remove = mall1.stores[0].inside_store_ids.index(person_id)
                    mall1.stores[0].exitStore(idx_to_remove)

                if len(mall1.waiting_mall_ids) > 0:
                    #!NOT WORKING YET
                    mall1.waiting_list_to_inside_mall()

        elif choices(pop, weights)[0] == 3:
            # e condicao para verificar se respeitou o limite
            if len(mall1.inside_mall_ids) >= mall1.mall_limit:
                mall1.waiting_outside_Mall(id)
                id += 1

        else:
            #e condicao para verificar se se cansou de esperar na fila
            if len(mall1.inside_mall_ids) >= mall1.mall_limit and len(mall1.waiting_mall_ids) > 0:
                idx_temp = randint(0 , len(mall1.waiting_mall_ids) - 1)
                mall1.stop_waiting_outside_Mall(idx_temp)

        #!para depois contar quantas pessoas estao em cada cena, provavlemente vai se a cada lista ver os status de cada pessoa
        print("--------------START--------------")
        print("Inside mall - ", mall1.inside_mall_ids)
        print("Waiting mall - ", mall1.waiting_mall_ids)
        print("LEN - ", len(mall1.stores[0].inside_store_ids))
        print("INSIDE ZARA - ", mall1.stores[0].inside_store_ids)
        print("WAITING ZARA - ", mall1.stores[0].waiting_store_ids)
        print("--------------END--------------")

        """#escolha da loja para fazer cenas randoms
        #list_idx = randint(0, len(mall1.stores))
        print(mall1.stores[0].store_name)
        #print("Store - ", (mall1_1.stores)[1])
        exit(0)"""
        time.sleep(0.2)
