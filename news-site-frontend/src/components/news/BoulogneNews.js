import React, { useCallback, useEffect, useState } from "react";
import Article from "./Article";
import axios from "axios";
import DefaultPagination from "../paginations/DefaultPagination";
import { Alert, Spinner } from "@material-tailwind/react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const BoulogneNews = () => {
  const [news, setNews] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = 10;
  const [stompClient, setStompClient] = useState(null);

  const connectWebSocket = useCallback(() => {
    const socket = new SockJS(`http://localhost:8080/ws`);
    const client = Stomp.over(socket);
    client.connect(
      {},
      () => {
        client.subscribe("/topic/articles", (message) => {
          const updatedArticles = JSON.parse(message.body);
          setNews(updatedArticles);
        });
        setStompClient(client);
      },
      (error) => {
        console.error("WebSocket connection error:", error);
        setError("Erreur lors de la connexion au serveur WebSocket.");
      }
    );
  }, []);

  const fetchNews = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      const url = `http://localhost:8080/api/scraping/news?page=${currentPage}`;
      const response = await axios.get(url);
      setNews(response.data);
    } catch (error) {
      console.error("Error fetching news:", error);
      setError("Erreur lors du chargement des articles de Boulogne.");
    } finally {
      setIsLoading(false);
    }
  }, [currentPage]);

  useEffect(() => {
    connectWebSocket();
    fetchNews();
  }, [connectWebSocket, fetchNews]);

  // Assurez-vous de fermer la connexion WebSocket lors de la destruction du composant
  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, [stompClient]);

  return (
    <div className="container mx-auto">
      {isLoading ? (
        <div className="flex justify-center items-center m-20 ">
          <Spinner className="h-16 w-16" color="green" />
        </div>
      ) : error ? (
        <div className="flex justify-center items-center m-20 ">
          <Alert color="red">{error}</Alert>
        </div>
      ) : news.length > 0 ? (
        <>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-8">
            {news.map((article, index) => (
              <Article key={index} {...article} />
            ))}
          </div>
          <div className="flex justify-center mt-4">
            <DefaultPagination
              totalPages={totalPages}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
            />
          </div>
        </>
      ) : (
        <div className="text-center mt-4 text-gray-500">
          Aucun article trouvé.
        </div>
      )}
    </div>
  );
};

export default BoulogneNews;
