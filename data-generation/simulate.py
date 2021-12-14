class Mall:
    def __init__(self,mall_name, mall_limit,stores, opening_time, close_time):
        self.people_inside_mall = 0
        self.people_waiting_mall = 0
        self.mall_limit = mall_limit
        self.mall_name = mall_name
        self.stores = stores
        self.opening_time = opening_time
        self.close_time = close_time

    def enterMall(self):
        #verificar se se pode entrar por causa do limite (verificar tambem se a pessoa respeitou ou nao)
        self.people_inside_mall += 1

    def exitMall(self):
        #verficiar se ha gente no mall
        self.people_inside_mall -= 1
    
    def waiting_outside_Mall(self):
        #verificar se o limit foi atingido
        self.people_waiting_mall += 1

    def stop_waiting_outside_Mall(self):
        self.people_waiting_mall -= 1
        #verificar se ha gente a espera

class Store:
    def __init__(self, store_name, store_limit, opening_time, close_time):
        self.store_name = store_name
        self.store_limit = store_limit
        self.opening_time = opening_time
        self.close_time = close_time
        self.people_inside_store = 0
        self.people_waiting_store = 0

    def enterStore(self):
        self.people_inside_store += 1
    
    def exitStore(self):
        self.people_inside_store -= 1

    def waiting_outside_Store(self):
        self.people_waiting_store += 1 