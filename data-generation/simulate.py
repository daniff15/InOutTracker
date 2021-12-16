from random import random

class Mall:
    def __init__(self,mall_name, mall_limit,stores, opening_time, close_time):
        self.mall_limit = mall_limit
        self.mall_name = mall_name
        self.stores = stores
        self.opening_time = opening_time
        self.close_time = close_time
        self.inside_mall_ids = []
        self.waiting_mall_ids = []

    def enterMall(self, id):
        #verificar se se pode entrar por causa do limite (verificar tambem se a pessoa respeitou ou nao)
        self.inside_mall_ids.append(id)

    def exitMall(self, id):
        #verficiar se ha gente no mall
        self.inside_mall_ids.remove(self.inside_mall_ids[id])
    
    def waiting_outside_Mall(self, id):
        #verificar se o limit foi atingido
        self.waiting_mall_ids.append(id)

    def stop_waiting_outside_Mall(self, id):
        self.waiting_mall_ids.remove(self.waiting_mall_ids[id])
        #verificar se ha gente a espera

    def waiting_list_to_inside_mall(self):
        if len(self.waiting_mall_ids) > 0:
            self.inside_mall_ids.append(self.waiting_mall_ids[0])
            self.waiting_mall_ids.remove(self.waiting_mall_ids[0])

class Store:
    def __init__(self, store_name, store_limit, opening_time, close_time):
        self.store_name = store_name
        self.store_limit = store_limit
        self.opening_time = opening_time
        self.close_time = close_time
        self.inside_store_ids = []
        self.waiting_store_ids = []

    def enterStore(self, id):
        self.inside_store_ids.append(id)
    
    def exitStore(self, id):
        self.inside_store_ids.remove(self.inside_store_ids[id])

    def waiting_outside_Store(self, id):
        self.waiting_store_ids.append(id) 

    def stop_waiting_store(self, id):
        self.waiting_store_ids.remove(self.waiting_store_ids[id])

    def waiting_list_to_inside_store(self):
        if len(self.waiting_store_ids) > 0:
            self.inside_store_ids.append(self.waiting_store_ids[0])
            self.waiting_store_ids.remove(self.waiting_store_ids[0])
